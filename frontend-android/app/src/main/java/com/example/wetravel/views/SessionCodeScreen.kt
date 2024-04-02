package com.example.wetravel.views

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wetravel.R
import com.example.wetravel.components.LogoTopAppBar
import com.example.wetravel.models.Resource
import com.example.wetravel.models.Trip
import com.example.wetravel.models.UserViewModel

val dmSansFamily = FontFamily(
    Font(
        resId = R.font.dmsans_semibold, FontWeight(600)
    )
)

@Composable
fun BackButton(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .padding(20.dp)
    ) {
        Text("go back", fontFamily = dmSansFamily)
    }
}

@Composable
fun EnterCodeContent(
    innerpadding: PaddingValues,
    code: String,
    onCodeChange: (String) -> Unit,
    onJoinButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit

    
) {
    Column(
        modifier = Modifier.padding(innerpadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp, alignment = Alignment.CenterVertically
            )
        ) {
            OutlinedTextField(
                value = code,
                onValueChange = onCodeChange,
                label = { Text("enter code", fontFamily = dmSansFamily, fontSize = 24.sp) },
                modifier = Modifier.fillMaxWidth(0.6f),
                textStyle = TextStyle(textAlign = TextAlign.Center)
            )
            Button(
                onClick = { viewModel.joinTripByCode(code) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text("join", fontFamily = dmSansFamily)
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.Absolute.Left,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton(onBackButtonClicked)
        }
    }
}

@Composable
fun CreateCodeContent(
    innerpadding: PaddingValues,
    tripCode: String,
    onContinueButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier.padding(innerpadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp, alignment = Alignment.CenterVertically
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(tripCode, fontFamily = dmSansFamily, fontSize = 24.sp)
            }
            Button(
                onClick = { /* TODO */ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text("copy share code", fontFamily = dmSansFamily)
            }
            Button(
                onClick = { onContinueButtonClicked() },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text("continue", fontFamily = dmSansFamily)
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            horizontalArrangement = Arrangement.Absolute.Left,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "note: you can always access this code from the settings page",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(50.dp),
                fontFamily = dmSansFamily
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SessionCodeScreen(
    onContinueButtonClicked: () -> Unit,
    userViewModel: UserViewModel
) {
    val tripCodeResource by userViewModel.tripCode.observeAsState(initial = Resource.Loading)
    Scaffold(topBar = {
        LogoTopAppBar()
    }) { innerpadding ->
        when (tripCodeResource) {
            is Resource.Success -> {
                val tripCode = (tripCodeResource as Resource.Success<String>).data
                CreateCodeContent(innerpadding, tripCode, onContinueButtonClicked)
            }
            is Resource.Loading -> {
                CircularProgressIndicator()
            }
            is Resource.Error -> {
                val errorMessage = (tripCodeResource as Resource.Error).message
                // Display the error message
                Text(text = "Error: $errorMessage")
            }
        }

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun JoinSessionScreen(onJoinButtonClicked: () -> Unit, onBackButtonClicked: () -> Unit, userViewModel: UserViewModel
) {
    var code by remember { mutableStateOf("") }



    Scaffold(topBar = {
        LogoTopAppBar()
    }) { innerpadding ->
        EnterCodeContent(
            innerpadding,
            code,
            { code = it },
            // replace this with below later
            /* { handleonJoinButtonClicked(code, userViewModel)},*/
            onJoinButtonClicked,
            onBackButtonClicked
        )
    }
}


// Button on-click functions

//private fun handleonJoinButtonClicked(
//    code: String,
//    userViewModel: UserViewModel
//) {
//    userViewModel.loadTrip(code)
//}