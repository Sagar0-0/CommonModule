package fit.asta.health.subscription.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.subscription.remote.model.SubscriptionResponse
import java.util.Calendar

@Composable
fun UserSubscribedPlanSection(userSubscribedPlan: SubscriptionResponse.UserSubscribedPlan) {
    val calendar = remember {
        Calendar.getInstance()
    }

    AppCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            TitleTexts.Level1(text = userSubscribedPlan.plan)
            TitleTexts.Level3(text = "Expires by: " + userSubscribedPlan.expBy)
        }
    }
}