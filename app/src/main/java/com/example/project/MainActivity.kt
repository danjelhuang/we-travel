package com.example.project

import AddDestinations
import TripConfigurationForm
import TripCreateorJoin
import TripLoginSignup
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project.Views.JoinSessionScreen
import com.example.project.Views.SessionCodeScreen
import com.example.project.ui.theme.ProjectTheme
import com.example.project.views.CreateAccountForm
import com.example.project.views.DestinationsList
import com.example.project.views.DestinationsVotingList
import com.example.project.views.VotingResultsMainScreen

enum class Screens() {
    Login,
    CreateAccount,
    TripCreateOrJoin,
    TripConfiguration,
    SessionCode,
    JoinSession,
    DestinationsListScreen,
    AddDestination,
    VotingScreen,
    VotingResults
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainLogo: Drawable = resources.getDrawable(R.drawable.logo_wetravel_main, null)

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
fun WeTravelApp(navController: NavHostController = rememberNavController()) {
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
                "Trip",
                onCreateTripButtonClicked = { navController.navigate(Screens.SessionCode.name) }
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
                onStartVotingButtonClicked = { navController.navigate(Screens.VotingScreen.name) }
            )
        }
        composable(route = Screens.AddDestination.name) {
            AddDestinations(
                onAddDestinationButtonClicked = { navController.navigate(Screens.DestinationsListScreen.name) }
            )
        }
        composable(route = Screens.VotingScreen.name) {
            DestinationsVotingList(
                onEndVotingButtonClicked = { navController.navigate(Screens.VotingResults.name) }
            )
        }
        composable(route = Screens.VotingResults.name) {
            VotingResultsMainScreen()
        }

    }
}