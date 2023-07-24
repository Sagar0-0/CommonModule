package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.common.ui.theme.ScheduleBackground

@Composable
fun ScheduleCardsImage() {
    Image(painter = painterResource(id = R.drawable.weatherimage),
        contentDescription = null,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .size(height = 130.dp, width = 100.dp),
        contentScale = ContentScale.Crop)
}

@Composable
fun ScheduleTodo(todo: String) {
    Text(text = todo,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Left)
}

@Composable
fun ScheduleCardTitle(title: String, value: String) {

    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Left)

        Spacer(modifier = Modifier.width(16.dp))


        Text(text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary)
    }


}

@Composable
fun ScheduleCardSelectionText(title: String) {
    ClickableText(text = AnnotatedString(text = title),
        onClick = { /*TODO*/ },
        style = TextStyle(fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.primary))
}

@Composable
fun OutlineBtn(text: String) {

    OutlinedButton(onClick = { /*TODO*/ },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary)) {
        Text(text = text,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal)
    }

}

@Composable
fun OutlineBtnTick(text: String) {

    OutlinedButton(onClick = { /*TODO*/ },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
        contentPadding = PaddingValues(8.dp)) {
        Icon(painter = painterResource(id = R.drawable.ic_tick),
            contentDescription = null,
            modifier = Modifier.size(19.dp),
            tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = text,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal)
    }

}

@Composable
fun WaterQuantitySelection(value: String) {
    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
        contentPadding = PaddingValues(8.dp),
    ) {
        Text(text = value, fontSize = 10.sp, fontWeight = FontWeight.Normal, color = Color.Black)
    }
}

@Composable
fun CardValue(cardValue: String) {

    Surface(shape = RoundedCornerShape(21.dp),
        color = ScheduleBackground,
        contentColor = MaterialTheme.colorScheme.primary) {
        Text(text = cardValue,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            textAlign = TextAlign.Center)
    }


}

@Composable
fun TodoAndSelection(time: String, scheduleTtl: String) {
    Row(Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        ScheduleTodo(todo = time)
        ScheduleCardSelectionText(title = scheduleTtl)
    }
}

@Composable
fun DoctorImg() {

    Image(painter = painterResource(id = R.drawable.weatherimage),
        contentDescription = null,
        modifier = Modifier
            .clip(CircleShape)
            .size(100.dp),
        contentScale = ContentScale.Crop)

}