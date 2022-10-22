package fit.asta.health.new_testimonials.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.profile.view.components.AddressType
import fit.asta.health.profile.view.components.ButtonListTypes
import fit.asta.health.profile.view.components.UpdateButton


@Composable
fun MyTextField() {

    val maxChar = 50

    var text by remember { mutableStateOf("") }

    Column(Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        OutlinedTextField(value = text,
            onValueChange = {
                if (it.length <= maxChar) text = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            placeholder = {
                Text(text = "Write your Testimonials",
                    fontSize = 14.sp,
                    lineHeight = 19.6.sp,
                    color = Color(0xff999999))
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White,
                focusedBorderColor = Color(0xff0088FF),
                unfocusedBorderColor = Color(0xffDFE6ED)))
        Text(text = "${text.length} / $maxChar",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp))
    }
}

@Composable
fun TestimonialTitle(
    placeHolder: String,
) {

    var text by remember { mutableStateOf("") }

    Column(Modifier
        .fillMaxWidth()) {
        OutlinedTextField(value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text(text = placeHolder,
                    fontSize = 14.sp,
                    lineHeight = 19.6.sp,
                    color = Color(0xff999999))
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White,
                focusedBorderColor = Color(0xff0088FF),
                unfocusedBorderColor = Color(0xffDFE6ED)))
    }
}


@Composable
fun TestimonialType() {

    val radioButtonList = listOf(ButtonListTypes(buttonType = "Written"),
        ButtonListTypes(buttonType = "Video"),
        ButtonListTypes(buttonType = "Image"))

    AddressType(selectionTypeText = "Type of Testimonials", radioButtonList = radioButtonList)
}


@Preview
@Composable
fun TestimonialLayout() {
    Column(Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Icon(painter = painterResource(id = R.drawable.removeicon),
                contentDescription = null,
                Modifier.size(24.dp))
        }

        TestimonialTitle(placeHolder = "Title")

        Spacer(modifier = Modifier.height(16.dp))

        TestimonialType()

        Spacer(modifier = Modifier.height(16.dp))

        TestimonialTitle(placeHolder = "Write your Testimonials")

        Spacer(modifier = Modifier.height(16.dp))

        TestimonialTitle(placeHolder = "Lisa")

        Spacer(modifier = Modifier.height(16.dp))

        TestimonialTitle(placeHolder = "Role")

        Spacer(modifier = Modifier.height(16.dp))

        TestimonialTitle(placeHolder = "Organisation")

        Spacer(modifier = Modifier.height(16.dp))

        TestimonialTitle(placeHolder = "Subtitle")

        Spacer(modifier = Modifier.height(16.dp))

        UpdateButton()
    }
}


