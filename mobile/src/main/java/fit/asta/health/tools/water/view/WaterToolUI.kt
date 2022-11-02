package fit.asta.health.tools.water.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.R
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter.State.Empty.painter
import fit.asta.health.tools.sunlight.view.components.CircularSlider
import fit.asta.health.ui.theme.md_theme_light_background
import fit.asta.health.ui.theme.md_theme_light_onBackground

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CardProgress()
        }
    }
}

@Composable
fun CardProgress() {
    Card(
        //elevation = 8.dp,
         shape = RoundedCornerShape(8.dp),
         modifier = Modifier
             .fillMaxWidth()
             .wrapContentHeight()
             .padding(16.dp)

    )
    {

        /*Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) */
        //{
            CircularProgress()
            /*CircularProgressIndicator(
                modifier = Modifier
                    .size(200.dp,200.dp),
                    progress = 0.75f)
                Text(text = "Hello")*/
        //}

    }
}

@Composable
fun CircularProgress(){
    Surface(
        modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
    ) { Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center)
    {
        CircularSlider(
            modifier = Modifier.size(200.dp,200.dp),
            stroke = 30f,
            //progressColor = colorResource(id = R.drawable.gradient_color),
            progressColor = Color.Blue,
            backgroundColor = Color.Black,
            thumbColor = Color.Green
        )
        Column(

        ) {

            Text(text = "Total",
            fontSize = 14.sp,
            lineHeight = 16.sp,
            letterSpacing = 1.25.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Text(text = "3 Litres",
            fontSize = 24.sp,
            lineHeight = 16.sp,
            letterSpacing = 1.25.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
    }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface(Modifier.fillMaxSize()) {
        CardProgress()
    }

}