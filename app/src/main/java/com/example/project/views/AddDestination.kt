

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.ButtonDefaults

@Composable
fun AddDestinations() {

    val destinations = listOf(
        "CN tower",
        "Yonge Dundas Square",
        "Ripleys Aquarium",
        "Union Station",
        "AGO Museum",
        "Harbourfront",
        "Kensington market",
        "University of Toronto",
        "Subway"
    )


    var category by remember {
        mutableStateOf("")
    }

    val heightTextFields by remember {
        mutableStateOf(55.dp)
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }

    // Category Field
    Column(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    expanded = false
                }
            )
    ) {

        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(Modifier.weight(1f))

            IconButton(
                onClick = { /* your click handler */ },
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "person",
                  //  tint = Color.Black
                )
            }
            Text(
                text = "4", // Replace with your actual number
                color = Color.Black,
                fontSize = 24.sp, // Adjust the font size as needed
                modifier = Modifier
                    .padding(start = 0.dp, top = 7.dp) // Adjust the padding as needed
            )
            IconButton(
                onClick = { /* your click handler */ },
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = "settings",
                )
            }
        }

        Text(
            modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
            text = "Add Destinations",
            fontSize = 16.sp,
            color = Color.Black,
            onTextLayout = {}
        )

        Column(modifier = Modifier.fillMaxWidth(),) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields)
                        .border(
                            width = 3.dp,
                            color = Color(red = 69, green = 123, blue = 157),
                            shape = RoundedCornerShape(10.dp)
                        )

                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },

                    value = category,
                    onValueChange = {
                        category = it
                        expanded = false
                    },
                    placeholder = { Text("Enter a Destination",
                        onTextLayout = {}) },
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "search",
                                tint = Color.Black
                            )
                        }
                    }
                )
            }

            AnimatedVisibility(visible = expanded) {
                Card(
                    modifier = Modifier

                        // .padding(horizontal = 5.dp)
                        .width(textFieldSize.width.dp)
                        .border(
                            width = 3.dp,
                            color = Color(red = 69, green = 123, blue = 157),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    //shape = RoundedCornerShape(10.dp)
                ) {

                    LazyColumn(
                        modifier = Modifier.heightIn(max = 300.dp),
                    ) {

                        if (category.isNotEmpty()) {
                            items(
                                destinations.filter {
                                    it.lowercase()
                                        .contains(category.lowercase()) || it.lowercase()
                                        .contains("others")
                                }
                                    .sorted()
                            ) {
                                ItemsCategory(title = it) { title ->
                                    category = title
                                    expanded = false
                                }
                            }
                        } else {
                            items(
                                destinations.sorted()
                            ) {
                                ItemsCategory(title = it) { title ->
                                    category = title
                                    expanded = false
                                }
                            }
                        }

                    }

                }
            }
        }
        Button(onClick = {},
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(red = 69, green = 123, blue = 157)
            ),
        ) {
            Text("Add")

        }
    }
}

@Composable
fun ItemsCategory(
    title: String,
    onSelect: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            //.height(55.dp)
            .clickable {
                onSelect(title)
            }

            .padding(15.dp)
    ) {
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium,
            onTextLayout = {})
    }

}