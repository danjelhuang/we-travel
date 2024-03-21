package com.example.wetravel

import AddDestinations
import TripConfigurationForm
import TripCreateorJoin
import TripLoginSignup
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wetravel.views.JoinSessionScreen
import com.example.wetravel.views.SessionCodeScreen
import com.example.wetravel.ui.theme.ProjectTheme
import com.example.wetravel.views.CreateAccountForm
import com.example.wetravel.views.DestinationsList
import com.example.wetravel.views.DestinationsVotingList
import com.example.wetravel.views.VotingResultsMainScreen
import kotlinx.coroutines.CoroutineScope

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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeTravelApp()
                }
            }
        }
    }
}

@Composable
fun WeTravelApp(navController: NavHostController = rememberNavController(), coroutineScope: CoroutineScope = rememberCoroutineScope()) {
    NavHost(
        navController = navController,
        startDestination = Screens.Login.name,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = Screens.Login.name) {
            TripLoginSignup(
                onSigninButtonClicked = { navController.navigate(Screens.TripCreateOrJoin.name) },
                onSignupButtonClicked = { navController.navigate(Screens.CreateAccount.name) }
            )
        }
        composable(route = Screens.TripCreateOrJoin.name) {
            TripCreateorJoin(
                onCreateTripButtonClicked = { navController.navigate(Screens.TripConfiguration.name) },
                onJoinTripButtonClicked = { navController.navigate(Screens.JoinSession.name) }
            )
        }
        composable(route = Screens.CreateAccount.name) {
            CreateAccountForm(
                onCreateAccountButtonClicked = { navController.navigate(Screens.TripCreateOrJoin.name) }
            )
        }
        composable(route = Screens.TripConfiguration.name) {
            TripConfigurationForm(
                "create",
                onButtonClicked = { navController.navigate(Screens.SessionCode.name) },
                coroutineScope
            )
        }
        composable(route = Screens.JoinSession.name) {
            JoinSessionScreen(
                onJoinButtonClicked = { navController.navigate(Screens.DestinationsListScreen.name) },
                onBackButtonClicked = { navController.navigate(Screens.TripCreateOrJoin.name) }
            )
        }
        composable(route = Screens.SessionCode.name) {
            SessionCodeScreen(
                onContinueButtonClicked = { navController.navigate(Screens.DestinationsListScreen.name) }
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
                onAddDestinationButtonClicked = { navController.navigate(Screens.DestinationsListScreen.name) },
                onSettingsButtonClicked = { navController.navigate(Screens.EditTrip.name) }
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
                coroutineScope
            )
        }

    }
}