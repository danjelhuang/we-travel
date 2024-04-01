package com.example.wetravel

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

import AddDestinations
import TripConfigurationForm
import TripLoginSignup
import android.app.Activity.RESULT_OK
import android.content.Context
import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavHostController
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
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}

class MainActivity : ComponentActivity() {

    private val googleAuthUIClient by lazy {
        GoogleAuthUIClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Define the API Managers below
        val tripRepository = TripRepository(apiService = RetrofitBuilder.apiService)
        val userRepository = UserRepository(apiService = RetrofitBuilder.apiService)

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
                        lifecycleScope = lifecycleScope,
                        applicationContext = applicationContext
                    )
                }
            }
        }
    }
}

@Composable
fun WeTravelApp(
    navController: NavHostController = rememberNavController(),
    tripRepository: TripRepository,
    userRepository: UserRepository,
    googleAuthUIClient: GoogleAuthUIClient,
    lifecycleScope: CoroutineScope,
    applicationContext: Context
) {
    val userViewModel = UserViewModel(tripRepository = tripRepository, userRepository = userRepository)
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
                    userViewModel.getAllTrips(googleAuthUIClient.getSignedInUser()?.userId ?: "")

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
                    userViewModel.getAllTrips(googleAuthUIClient.getSignedInUser()?.userId ?: "")
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
                userViewModel = userViewModel)
        }
        composable(route = Screens.CreateAccount.name) {
            CreateAccountForm(onCreateAccountButtonClicked = { navController.navigate(Screens.TripCreateOrJoin.name) })
        }
        composable(route = Screens.TripConfiguration.name) {
            TripConfigurationForm(
                "create",
                onButtonClicked = { navController.navigate(Screens.SessionCode.name) },
                userViewModel = userViewModel
            )
        }
        composable(route = Screens.JoinSession.name) {
            JoinSessionScreen(
                onJoinButtonClicked = { navController.navigate(Screens.DestinationsListScreen.name) },
                onBackButtonClicked = { navController.navigate(Screens.TripCreateOrJoin.name) },
                userViewModel = userViewModel
            )
        }
        composable(route = Screens.SessionCode.name) {
            SessionCodeScreen(
                onContinueButtonClicked = { navController.navigate(Screens.DestinationsListScreen.name) },
                userViewModel = userViewModel
            )
        }
        composable(route = Screens.DestinationsListScreen.name) {
            DestinationsList(
                onAddDestinationButtonClicked = { navController.navigate(Screens.AddDestination.name) },
                onStartVotingButtonClicked = { navController.navigate(Screens.VotingScreen.name) },
                onSettingsButtonClicked = { navController.navigate(Screens.EditTrip.name) }
            )
        }
        composable(route = Screens.AddDestination.name) {
            AddDestinations(
                onAddDestinationButtonClicked = { navController.navigate(Screens.DestinationsListScreen.name) }
            )
        }
        composable(route = Screens.VotingScreen.name) {
            DestinationsVotingList(
                onEndVotingButtonClicked = { navController.navigate(Screens.VotingResults.name) },
                onSettingsButtonClicked = { navController.navigate(Screens.EditTrip.name) }
            )
        }
        composable(route = Screens.VotingResults.name) {
            VotingResultsMainScreen()
        }
        composable(route = Screens.EditTrip.name) {
            TripConfigurationForm(
                "edit",
                onButtonClicked = { navController.popBackStack() },
                userViewModel = userViewModel
            )
        }

    }
}