package fit.asta.health.feature.sunlight.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.data.sunlight.model.network.response.ResponseData
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R

@Composable
fun UpcomingSlotsCard(apiState: ResponseData.SunlightToolData) {

    AppCard(
        modifier = Modifier
            .fillMaxWidth(),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                Row {
                    Box {
                        AppIcon(
                            painter = painterResource(id = R.drawable.ic_sunny),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = Color(0xffFED85B)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    TitleTexts.Level3(
                        text = "Sunny\n24 C",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }

                AppIconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(4.dp))
                        .size(24.dp),
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0x66000001))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        AppIcon(
                            painter = painterResource(id = com.google.android.material.R.drawable.ic_clock_black_24dp),
                            contentDescription = null,
                            tint = Color(0xff0088FF),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CaptionTexts.Level2(
                    text = "Today",
                    color = Color.White,
                )
                CaptionTexts.Level2(
                    text = "11:00 am",
                    color = Color.White,
                )
            }

        }
    }
}