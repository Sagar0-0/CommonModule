package fit.asta.health.navigation.home.view.component

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.R
import fit.asta.health.common.ui.theme.RateUsGrad1
import fit.asta.health.common.ui.theme.RateUsGrad2
import fit.asta.health.navigation.home.viewmodel.RateUsEvent
import fit.asta.health.navigation.home.viewmodel.RateUsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun RateUsCard(activity: Activity, viewModel: RateUsViewModel = hiltViewModel()) {

    LaunchedEffect(key1 = viewModel.state.reviewInfo) {
        viewModel.state.reviewInfo?.let {
            viewModel.reviewManager.launchReviewFlow(activity, it)
        }
    }

    FontFamily(Font(R.font.inter_regular, FontWeight.Medium))
    val interMediumFontFamily = FontFamily(Font(R.font.inter_medium, FontWeight.Medium))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(124.dp)
            .clip(RoundedCornerShape(8.dp))
            .shadow(elevation = 4.dp),
        onClick = {
            viewModel.onEvent(RateUsEvent.InAppReviewRequested)
        },
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 9.dp, bottom = 9.dp, end = 13.5.dp, start = 6.5.dp)
        ) {
            Box(Modifier.size(width = 130.dp, height = 106.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.rate_us_image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Box {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Rate Us",
                        fontSize = 16.sp,
                        fontFamily = interMediumFontFamily,
                        color = Color.Black,
                        lineHeight = 24.sp
                    )
                    Text(
                        text = "We value your feedback pls let us know how we are doing by rating us.",
                        fontSize = 12.sp,
                        fontFamily = interMediumFontFamily,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            viewModel.onEvent(RateUsEvent.InAppReviewRequested)
                        },
                        Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .height(28.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        (
                                                RateUsGrad1
                                                ), (RateUsGrad2)
                                    )
                                )
                            ),
                        contentPadding = PaddingValues(vertical = 2.dp, horizontal = 8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                    ) {
                        Text(
                            text = "Rate Us",
                            fontSize = 14.sp,
                            fontFamily = interMediumFontFamily,
                            color = Color.White,
                            letterSpacing = 0.15.sp
                        )
                    }
                }
            }
        }
    }
}