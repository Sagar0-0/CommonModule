@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package fit.asta.health.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import fit.asta.health.R
import fit.asta.health.home.view.AutoSliding
import fit.asta.health.home.view.AutoSlidingTestimonials

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(modifier = Modifier
                .background(color = MaterialTheme.colors.background)
                .clip(RoundedCornerShape(16.dp))) {
                Column(Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .background(color = MaterialTheme.colors.background)
                    .verticalScroll(rememberScrollState())) {
                    NameAndMoodHomeScreenHeader()
                    Spacer(modifier = Modifier.height(24.dp))
                    WeatherCardImage()
                    Spacer(modifier = Modifier.height(24.dp))
                    AutoSliding()
                    MyToolsAndViewAll()
                    VerticalImageCards()
                    Testimonials()
                    Spacer(modifier = Modifier.height(24.dp))
                    RateUsCard()
                    Spacer(modifier = Modifier.height(24.dp))
                    ReferAndEarn()
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}


@Composable
fun WeatherCardImage() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(151.dp)
        .clip(RoundedCornerShape(10.dp))
    ) {
        Image(painter = painterResource(id = R.drawable.weatherimage),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter))
        TemperatureAndWeather()
        LocationAndDate()
    }
}

@Composable
fun TemperatureAndWeather() {

    val interFontFamily = FontFamily(
        Font(R.font.inter_regular, FontWeight.Normal)
    )

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 14.dp, end = 18.dp, top = 10.dp, bottom = 62.41.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(100.dp, 80.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Box {
                    Text(
                        text = "18",
                        fontFamily = interFontFamily,
                        fontSize = 72.sp,
                        color = Color.White,
                        modifier = Modifier.align(alignment = Alignment.TopCenter)
                    )
                }
                Box(Modifier.size(13.5.dp)) {
                    Image(painter = painterResource(id = R.drawable.temperaturedegreeimage),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit)
                }
            }
        }

        Box(modifier = Modifier.size(63.dp, 58.dp)) {
            Image(painter = painterResource(id = R.drawable.rainimage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds)
        }
    }
}

@Composable
fun LocationAndDate() {

    val interFontFamily = FontFamily(
        Font(R.font.inter_regular, FontWeight.Normal)
    )

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 14.dp, end = 18.dp, top = 104.dp, bottom = 23.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(154.dp, 24.dp)) {
            Image(painter = painterResource(id = R.drawable.location),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                contentScale = ContentScale.Fit)
            Text(text = "Dwarka, Delhi",
                fontFamily = interFontFamily,
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .padding(start = 4.dp), fontSize = 14.sp, color = Color(0xFFFFFFFF))
        }
        Text(text = "Friday, 24 June",
            color = Color.White,
            fontFamily = interFontFamily,
            fontSize = 14.sp)
    }
}

@Composable
fun MyToolsAndViewAll() {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)) {
        Text(text = "MyTools", style = MaterialTheme.typography.h6, color = Color.Black)
        Box(modifier = Modifier.clickable(enabled = true, onClick = ({}))) {
            Text(text = "All Tools", style = MaterialTheme.typography.h6, color = Color.Blue)
        }
    }
}

@Composable
fun VerticalImageCards() {
    Column(Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()) {
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.waterimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }
                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Water",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.breathingimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }

                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Breathing",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Row(modifier = Modifier
            .fillMaxWidth()) {
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.meditationimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }

                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Meditation",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.sunlightimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }

                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Sunlight",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Row(modifier = Modifier
            .fillMaxWidth()) {
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.sleepimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }

                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Sleep",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.stepsimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }

                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Steps",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandedVerticalImageGrid() {
    Column(Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()) {
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.waterimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }
                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Water",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.breathingimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }

                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Breathing",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.meditationimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }

                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Meditation",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
        }


        Spacer(modifier = Modifier.padding(top = 16.dp))



        Row(modifier = Modifier
            .fillMaxWidth()) {
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.sunlightimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }

                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Sunlight",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.sleepimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }

                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Sleep",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.stepsimage),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                        Box(modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 8.dp),
                                contentScale = ContentScale.Fit)
                        }
                    }

                    Card(modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "Steps",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
                    }
                }
            }
        }
    }
}

@Composable
fun NameAndMoodHomeScreenHeader() {

    val poppinsFontFamily = FontFamily(
        Font(R.font.poppins_medium, FontWeight.Medium)
    )

    val interFontFamily = FontFamily(
        Font(R.font.inter_regular, FontWeight.Normal)
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .height(59.dp),
        verticalArrangement = Arrangement.SpaceBetween) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier
                .size(153.dp, 36.dp)) {
                Text(text = "Hello Aastha ",
                    fontSize = 24.sp,
                    fontFamily = poppinsFontFamily)
            }
            Box {
                Text(text = "\uD83D\uDC4B", fontSize = 24.sp,
                    fontFamily = poppinsFontFamily)
            }
        }
        Box(modifier = Modifier
            .size(147.dp, 15.dp)) {
            Text(text = "What’s your mood today ?",
                fontSize = 12.sp,
                fontFamily = interFontFamily,
                color = Color(0xFFA9A7B1))
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Testimonials() {
    FontFamily(
        Font(R.font.inter_regular, FontWeight.Normal)
    )

    FontFamily(Font(R.font.inter_medium))

    Column(Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier
            .height(26.dp)
            .align(Alignment.CenterHorizontally)
            .padding(top = 1.dp, bottom = 1.dp)) {
            Image(painter = painterResource(id = R.drawable.testimonials_tagline),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
        Divider(color = Color(0xFF0088FF), thickness = 4.dp, modifier = Modifier
            .width(71.dp)
            .clip(
                RoundedCornerShape(2.dp)))
        Spacer(modifier = Modifier.height(16.dp))
        AutoSlidingTestimonials()
    }
}

@Composable
fun RateUsCard() {
    FontFamily(
        Font(R.font.inter_regular, FontWeight.Medium)
    )

    val interMediumFontFamily = FontFamily(Font(R.font.inter_medium, FontWeight.Medium))

    Card(modifier = Modifier
        .fillMaxWidth()
        .height(124.dp)
        .clip(RoundedCornerShape(8.dp))
        .shadow(elevation = 4.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 9.dp, bottom = 9.dp, end = 13.5.dp, start = 6.5.dp)) {
            Box(Modifier.size(width = 130.dp, height = 106.dp)) {
                Image(painter = painterResource(id = R.drawable.rate_us_image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds)
            }
            Spacer(modifier = Modifier.width(20.dp))
            Box {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Rate Us",
                        fontSize = 16.sp,
                        fontFamily = interMediumFontFamily,
                        color = Color.Black, lineHeight = 24.sp)
                    Text(text = "We value your feedback pls let us know how we are doing by rating us.",
                        fontSize = 12.sp,
                        fontFamily = interMediumFontFamily,
                        color = Color(0xFF8694A9))
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = {},
                        Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .height(28.dp)
                            .background(brush = Brush.linearGradient(colors = listOf(
                                Color(0xFF0075FF),
                                Color(0xFF00D1FF)
                            ))),
                        contentPadding = PaddingValues(vertical = 2.dp, horizontal = 8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {
                        Text(text = "Rate Us",
                            fontSize = 14.sp,
                            fontFamily = interMediumFontFamily,
                            color = Color.White, letterSpacing = 0.15.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ReferAndEarn() {

    val interFontFamily = FontFamily(
        Font(R.font.inter_regular, FontWeight.Medium)
    )

    val interMediumFontFamily = FontFamily(Font(R.font.inter_medium, FontWeight.Medium))

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(132.dp)
        .background(Color.Transparent)) {
        Image(painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .shadow(elevation = 4.dp),
            contentScale = ContentScale.FillBounds)
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 9.dp, bottom = 9.dp, end = 13.5.dp, start = 6.5.dp)) {
            Box(Modifier.size(width = 130.dp, height = 106.dp)) {
                Image(painter = painterResource(id = R.drawable.refer_image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds)
            }
            Spacer(modifier = Modifier.width(20.dp))
            Box {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Refer and Earn",
                        fontSize = 16.sp,
                        fontFamily = interMediumFontFamily,
                        color = Color.Black, lineHeight = 24.sp)
                    Text(text = "Send referral link to your friend to earn ₹ 100",
                        fontSize = 12.sp,
                        fontFamily = interFontFamily,
                        color = Color(0xFF8694A9))
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = {},
                        Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .size(width = 53.dp, height = 28.dp),
                        contentPadding = PaddingValues(vertical = 2.dp, horizontal = 8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)) {
                        Text(text = "Refer",
                            fontSize = 14.sp,
                            fontFamily = interMediumFontFamily,
                            color = Color.White, letterSpacing = 0.15.sp)
                    }
                }
            }
        }
    }
}