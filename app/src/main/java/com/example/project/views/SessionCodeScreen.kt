package com.example.project.Views

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.project.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogoTopAppBar() {
    TopAppBar(title = { Text(text = "") }, navigationIcon = {
        Icon(
            painter = painterResource(id = R.drawable.wetravel_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(100.dp)
                .padding(start = 20.dp),
            tint = Color.Unspecified
        )

    })
}

@Composable
fun BackButton(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(red = 69, green = 123, blue = 157)
        ),
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .padding(20.dp)
    ) {
        Text("go back")
    }
}

@Composable
fun EnterCodeContent(innerpadding: PaddingValues, code: String, onCodeChange: (String) -> Unit) {
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
                label = { Text("enter code") },
                modifier = Modifier.fillMaxWidth(0.6f),
                textStyle = TextStyle(textAlign = TextAlign.Center)
            )
            Button(
                onClick = { /* TODO */ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(red = 69, green = 123, blue = 157)
                ),
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text("join")
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.Absolute.Left,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton {}
        }
    }
}

@Composable
fun CreateCodeContent(innerpadding: PaddingValues) {
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
                        Color(red = 69, green = 123, blue = 157),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("k7jcc56")
            }
            Button(
                onClick = { /* TODO */ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(red = 69, green = 123, blue = 157)
                ),
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text("copy share code")
            }
            Button(
                onClick = { /* TODO */ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(red = 69, green = 123, blue = 157)
                ),
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text("continue")
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.Absolute.Left,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton {}
        }
    }
}

@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SessionCodeScreen() {
    var code by remember { mutableStateOf("") }

    Scaffold(topBar = {
        LogoTopAppBar()
    }) {innerpadding ->
//        CreateCodeContent(innerpadding)
        EnterCodeContent(innerpadding, code) { code = it }
    }
}