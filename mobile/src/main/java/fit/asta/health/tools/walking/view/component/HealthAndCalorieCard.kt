package fit.asta.health.tools.walking.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts

@Preview
@Composable
fun HealthAndCalorieCard() {

    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        HealthComponentLayout()
    }

}

@Composable
fun HealthComponentLayout() {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly) {
        HealthComponent(title = "Distance", titleValue = "5 Km")
        HealthComponent(title = "Calories", titleValue = "400 kal")
        HealthComponent(title = "Heart Rate", titleValue = "72 bpm")
        HealthComponent(title = "Blood Pressure", titleValue = "120/80 hhmg")
    }

}


@Composable
fun HealthComponent(
    title: String,
    titleValue: String,
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        AppIcon(
            painter = painterResource(id = R.drawable.ic_baseline_favorite_24),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        BodyTexts.Level2(text = title, color = AppTheme.colors.onPrimary)
        Spacer(modifier = Modifier.height(8.dp))
        CaptionTexts.Level2(text = titleValue)
    }

}