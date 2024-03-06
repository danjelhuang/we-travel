
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project.R

@Composable
fun TripCreateorJoin(
    onCreateTripButtonClicked: () -> Unit,
    onJoinTripButtonClicked: () -> Unit
) {
    Column {
        TopBar()
        SelectionButtons(onCreateTripButtonClicked, onJoinTripButtonClicked)
    }
}

@Composable
fun SelectionButtons(
    onCreateTripButtonClicked: () -> Unit,
    onJoinTripButtonClicked: () -> Unit
) {
    val dmSansFamily = FontFamily(
        Font(
            resId = R.font.dmsans_semibold, FontWeight(600)
        )
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
    ) {

        Button(
            onClick = { onCreateTripButtonClicked() },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .height(125.dp)
                .width(2000.dp)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text(
                text = "create trip",
                fontFamily = dmSansFamily,
                fontSize = 36.sp /*color = Color.White*/
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onJoinTripButtonClicked() },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .height(125.dp)
                .width(2000.dp)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text(
                text = "join trip",
                fontFamily = dmSansFamily,
                fontSize = 36.sp /*color = Color.White*/
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {},
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.logo_we),
                contentDescription = "WeTravelLogo",
                modifier = Modifier.size(100.dp)
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}






