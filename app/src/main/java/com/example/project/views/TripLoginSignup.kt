import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TripLoginSignup(
    onSigninButtonClicked: () -> Unit,
    onSignupButtonClicked: () -> Unit
) {

    val username = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }

    val dmSansFamily = FontFamily(
        Font(
            resId = R.font.dmsans_semibold, FontWeight(600)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val imageModifier = Modifier
            .size(280.dp)
        Image(
            painter = painterResource(id = R.drawable.logo_wetravel_main),
            contentDescription = "WeTravelLogo",
            contentScale = ContentScale.FillWidth,
            modifier = imageModifier.aspectRatio(16f / 5f)
        )
        Spacer(Modifier.height(1.dp))

        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("username", fontFamily = dmSansFamily) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
            // colors = MaterialTheme.colorScheme.DarkestBlueOutline
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = {
                Text(
                    text = "password",
                    fontFamily = dmSansFamily
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
//            TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
//                unfocusedBorderColor = Color.White
//            )
        )

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = { onSigninButtonClicked() },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text(
                text = "sign in",
                color = Color.White,
                fontFamily = dmSansFamily,
                fontSize = 20.sp
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "don't have an account yet?",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            fontFamily = dmSansFamily
        )
        Text(
            text = "create account",
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable(onClick = { onSignupButtonClicked() }),
            fontFamily = dmSansFamily
        )

    }

}






