package com.example.wetravel

import AddDestinations
import TripCreateForm
import TripEditForm
import PlacesClientManager
import TripLoginSignup
import android.app.Activity.RESULT_OK
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wetravel.models.Destination
import com.example.wetravel.models.Trip
import com.example.wetravel.models.TripUsers
import com.example.wetravel.models.UserViewModel
import com.example.wetravel.presentation.sign_in.GoogleAuthUIClient
import com.example.wetravel.presentation.sign_in.SignInViewModel
import com.example.wetravel.service.ApiService
import com.example.wetravel.service.TripRepository
import com.example.wetravel.service.UserRepository
import com.example.wetravel.ui.theme.ProjectTheme
import com.example.wetravel.views.CreateAccountForm
import com.example.wetravel.views.DestinationsList
import com.example.wetravel.views.DestinationsVotingList
import com.example.wetravel.views.JoinSessionScreen
import com.example.wetravel.views.LandingPage
import com.example.wetravel.views.SessionCodeScreen
import com.example.wetravel.views.VotingResultsMainScreen
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPhotoResponse
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

enum class Screens() {
    Login,
    CreateAccount,
    TripCreateOrJoin,
    TripConfiguration,
    SessionCode,
    JoinSession,
    DestinationsListScreen,
    AddDestination,
    EditTrip,
    VotingScreen,
    VotingResults
}



// Define our backend link and deserialization library + init the apiService
// Currently the backend is just defined at 10.0.2.2:3000
object RetrofitBuilder {
    private fun getRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .registerTypeAdapter(Trip::class.java, TripDeserializer())
            .create()
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/api/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}

class TripDeserializer: JsonDeserializer<Trip> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Trip {
        val jsonObject = json?.asJsonObject ?: throw JsonParseException("Invalid JSON")

        val tripID = jsonObject.getAsJsonPrimitive("tripID").asString
        val name = jsonObject.getAsJsonPrimitive("name").asString
        val city = jsonObject.getAsJsonPrimitive("city").asString
        val adminUserID = jsonObject.getAsJsonPrimitive("adminUserID").asString
        val votesPerPerson = jsonObject.getAsJsonPrimitive("votesPerPerson").asInt
        val phase = jsonObject.getAsJsonPrimitive("phase").asString
        val finalDestinationCount = jsonObject.getAsJsonPrimitive("finalDestinationCount").asString

        val parsedUsers = parseUsersList(jsonObject.get("users"))
        val parsedDestinations = parseDestinationsList(jsonObject.get("destinationsList"))

        val trip = Trip(
            tripID = tripID,
            name = name,
            city = city,
            adminUserID = adminUserID,
            votesPerPerson = votesPerPerson,
            phase = phase,
            users = parsedUsers,
            destinationsList = parsedDestinations,
            finalDestinationCount = finalDestinationCount.toInt()
        )

        Log.d("DESERIALIZED", trip.toString())

        return trip

    }

    // TODO: Destinations list must be parsed properly
    // Here, create Destination objects by calling the google maps Place API
    private fun parseDestinationsList(jsonElement: JsonElement?) : List<Destination> {
        return emptyList()
    }

    private fun parseUsersList(usersJson: JsonElement?) : List<TripUsers> {
        val userList = mutableListOf<TripUsers>()
        usersJson?.let {
            if (it.isJsonArray) {
                val jsonArray = it.asJsonArray
                jsonArray.forEach { element ->
                    if (element.isJsonObject) {
                        val userObject = element.asJsonObject
//                        Log.d("JSON OB", element.toString())
                        try {
                            val userID = userObject.getAsJsonPrimitive("userID").asString
                            val votes = userObject.getAsJsonPrimitive("votes").asInt
                            userList.add(TripUsers(userID, votes))
                        } catch (e: Exception) {
                            // Handle unexpected fields or invalid format
                            Log.e("Parsing Error", "Error parsing user object: ${e.message}")
                        }
                    } else {
                        // Handle invalid format - expected object but found other type
                        Log.e("Parsing Error", "Invalid JSON format for user object: $element")
                    }
                }
            } else {
                // Handle invalid format - expected array but found other type
                Log.e("Parsing Error", "Invalid JSON format for users array: $it")
            }
        }

        return userList
    }

}

class MainActivity : ComponentActivity() {

    private val googleAuthUIClient by lazy {
        GoogleAuthUIClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    companion object {
        lateinit var placesClient : PlacesClient
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Define the API Managers below
        val tripRepository = TripRepository(apiService = RetrofitBuilder.apiService)
        val userRepository = UserRepository(apiService = RetrofitBuilder.apiService)
        PlacesClientManager.initialize(applicationContext)
        placesClient = PlacesClientManager.getPlacesClient()

        setContent {
            ProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeTravelApp(
                        tripRepository = tripRepository,
                        userRepository = userRepository,
                        googleAuthUIClient = googleAuthUIClient,
                        placesClient = placesClient,
                        lifecycleScope = lifecycleScope,
                        applicationContext = applicationContext
                    )
                }
            }
        }
    }
}
suspend fun getPlaceDetails(placeID: String): Destination {
    val placesClient = MainActivity.placesClient
    val placeFields = listOf(
        Place.Field.NAME,
        Place.Field.ADDRESS,
        Place.Field.RATING,
        Place.Field.USER_RATINGS_TOTAL,
        Place.Field.TYPES,
        Place.Field.PHOTO_METADATAS
    )

    val request = FetchPlaceRequest.newInstance(placeID, placeFields)

    return try {
        val response = placesClient.fetchPlace(request).await()
        val place = response.place

        val placeName = place.name ?: ""
        val placeAddress = place.address ?: ""
        val placeRating = place.rating ?: -1.0
        val placeReviewCount = place.userRatingsTotal ?: -1
        val placeType = place.placeTypes?.getOrNull(0) ?: ""

        var placeImageBitmap: Bitmap? = null

        // Check if the place has a photo metadata available
        if (place.photoMetadatas != null && place.photoMetadatas!!.isNotEmpty()) {
            // Get the first photo metadata
            val photoMetadata = place.photoMetadatas!![0]

            // Get the bitmap image data
            val photoRequest = FetchPhotoRequest.builder(photoMetadata)
                .setMaxWidth(550)
                .setMaxHeight(550)
                .build()

            val fetchPhotoResponse = placesClient.fetchPhoto(photoRequest).await()
            placeImageBitmap = fetchPhotoResponse.bitmap
        }

        Destination(
            placeId = placeID,
            name = placeName,
            address = placeAddress,
            rating = placeRating,
            reviewCount = placeReviewCount,
            type = placeType,
            imageBitmap = placeImageBitmap,
            totalVotes = 0,
            userVotes = 0
        )
    } catch (e: Exception) {
        // Handle failure
        println("FETCH PLACE DETAILS FAILED: $e")
        Destination(
            placeId = placeID,
            name = "",
            address = "",
            rating = -1.0,
            reviewCount = -1,
            type = "",
            imageBitmap = null,
            totalVotes = 0,
            userVotes = 0
        )
    }
}


@Composable
fun WeTravelApp(
    navController: NavHostController = rememberNavController(),
    tripRepository: TripRepository,
    userRepository: UserRepository,
    googleAuthUIClient: GoogleAuthUIClient,
    placesClient : PlacesClient,
    lifecycleScope: CoroutineScope,
    applicationContext: Context
) {
    val db = FirebaseFirestore.getInstance();
    val userViewModel = UserViewModel(
        tripRepository = tripRepository,
        userRepository = userRepository,
        db)
    NavHost(
        navController = navController,
        startDestination = Screens.Login.name,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = Screens.Login.name) {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = Unit) {
                if (googleAuthUIClient.getSignedInUser() != null) {
                    userViewModel.getOrCreateUser(googleAuthUIClient.getSignedInUser()?.userId ?: "")
                    navController.navigate(Screens.TripCreateOrJoin.name)
                }
            }

            val launcher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if (result.resultCode == RESULT_OK) {
                            lifecycleScope.launch {
                                val signInResult = googleAuthUIClient.signInWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                viewModel.onSignInResult(signInResult)
                            }
                        }
                    })

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(
                        applicationContext, "Sign in successful", Toast.LENGTH_LONG
                    ).show()

                    userViewModel.getOrCreateUser(googleAuthUIClient.getSignedInUser()?.userId ?: "")
                    navController.navigate(Screens.TripCreateOrJoin.name)
                    viewModel.resetState()
                }
            }

            TripLoginSignup(state = state, onSigninButtonClicked = {
                lifecycleScope.launch {
                    val signInIntentSender = googleAuthUIClient.signIn()
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender ?: return@launch
                        ).build()
                    )
                }
            })

        }
        composable(route = Screens.TripCreateOrJoin.name) {
            // TODO: We can get the user data here or inside the LandingPage composable
            LandingPage(userData = googleAuthUIClient.getSignedInUser(),
                onSignOut = {
                    lifecycleScope.launch {
                        googleAuthUIClient.signOut()
                        Toast.makeText(
                            applicationContext, "Signed out", Toast.LENGTH_LONG
                        ).show()
                        navController.navigate(Screens.Login.name)
                    }
                },
                onCreateTripButtonClicked = { navController.navigate(Screens.TripConfiguration.name) },
                onJoinTripButtonClicked = { navController.navigate(Screens.JoinSession.name) },
                onTripCardClicked = { phase ->
                    when (phase) {
                        "Adding" -> navController.navigate(Screens.DestinationsListScreen.name)
                        "Voting" -> navController.navigate(Screens.VotingScreen.name)
                        "Ended" -> navController.navigate(Screens.VotingResults.name)
                        else -> Log.d("Navigate to Trip", "Invalid Phase bro")
                    }
                },
                userViewModel = userViewModel)
        }
        composable(route = Screens.CreateAccount.name) {
            BackHandler(true) { navController.navigate(Screens.TripCreateOrJoin.name) }
            CreateAccountForm(onCreateAccountButtonClicked = { navController.navigate(Screens.TripCreateOrJoin.name) })
        }
        composable(route = Screens.TripConfiguration.name) {
            BackHandler(true) { navController.navigate(Screens.TripCreateOrJoin.name) }
            TripCreateForm(
                onButtonClicked = { navController.navigate(Screens.SessionCode.name) },
                userViewModel = userViewModel
            )
        }
        composable(route = Screens.JoinSession.name) {
            BackHandler(true) { navController.navigate(Screens.TripCreateOrJoin.name) }
            JoinSessionScreen(
                onJoinButtonClicked = { navController.navigate(Screens.DestinationsListScreen.name) },
                onBackButtonClicked = { navController.navigate(Screens.TripCreateOrJoin.name) },
                userViewModel = userViewModel
            )
        }
        composable(route = Screens.SessionCode.name) {
            BackHandler(true) { navController.navigate(Screens.TripCreateOrJoin.name) }
            SessionCodeScreen(
                onContinueButtonClicked = { navController.navigate(Screens.DestinationsListScreen.name) },
                userViewModel = userViewModel
            )
        }
        composable(route = Screens.DestinationsListScreen.name) {
            BackHandler(true) { navController.navigate(Screens.TripCreateOrJoin.name) }
            DestinationsList(
                onAddDestinationButtonClicked = { navController.navigate(Screens.AddDestination.name) },
                onStartVotingButtonClicked = { navController.navigate(Screens.VotingScreen.name) },
                onSettingsButtonClicked = { navController.navigate(Screens.EditTrip.name) },
                userViewModel = userViewModel,
                userName = googleAuthUIClient.getSignedInUser()?.username
            )
        }
        composable(route = Screens.AddDestination.name) {
            AddDestinations(
                onAddDestinationButtonClicked = { navController.navigate(Screens.DestinationsListScreen.name) },
                onLeaveButtonClicked = { navController.navigate(Screens.DestinationsListScreen.name)},
                userViewModel = userViewModel,
                placesClient = placesClient
            )
        }
        composable(route = Screens.VotingScreen.name) {
            BackHandler(true) { navController.navigate(Screens.TripCreateOrJoin.name) }
            DestinationsVotingList(
                onEndVotingButtonClicked = { navController.navigate(Screens.VotingResults.name) },
                onSettingsButtonClicked = { navController.navigate(Screens.EditTrip.name) },
                userViewModel = userViewModel
            )
        }
        composable(route = Screens.VotingResults.name) {
            BackHandler(true) { navController.navigate(Screens.TripCreateOrJoin.name) }
            VotingResultsMainScreen()
        }
        composable(route = Screens.EditTrip.name) {
            TripEditForm(
                onButtonClicked = { navController.popBackStack() },
                userViewModel = userViewModel
            )
        }

    }
}