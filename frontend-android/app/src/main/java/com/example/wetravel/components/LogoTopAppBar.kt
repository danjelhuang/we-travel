package com.example.wetravel.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wetravel.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogoTopAppBar() {
    TopAppBar(title = {}, navigationIcon = {
        Icon(
            painter = painterResource(id = R.drawable.logo_we),
            contentDescription = "Logo",
            modifier = Modifier
                .size(100.dp)
                .padding(start = 20.dp),
            tint = Color.Unspecified
        )

    })
}
