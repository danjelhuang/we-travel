import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun CreateAccountForm() {
    val firstName = remember { mutableStateOf(TextFieldValue()) }
    val lastName = remember { mutableStateOf(TextFieldValue()) }
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    var verifyPassword = remember { mutableStateOf(TextFieldValue()) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier
        .padding(16.dp)
        .fillMaxHeight()) {

        AccountInputField(label = "first name", value = firstName.value, onValueChange = { firstName.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        AccountInputField(label = "last name", value = lastName.value, onValueChange = { lastName.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        AccountInputField(label = "email", value = email.value, onValueChange = { email.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        AccountInputField(label = "password", value = password.value, onValueChange = { password.value = it })
        Spacer(modifier = Modifier.height(20.dp))
        AccountInputField(label = "verify password", value = verifyPassword.value, onValueChange = { verifyPassword.value = it })
        Spacer(modifier = Modifier.height(20.dp))


        Button(
            onClick = {  /* TODO */ },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(red = 69, green = 123, blue = 157)
            ),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally)){
            Text("create my account", color = Color.White)
        }
    }
}

@Composable
fun AccountInputField(label: String, value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 48.dp)
    )
}