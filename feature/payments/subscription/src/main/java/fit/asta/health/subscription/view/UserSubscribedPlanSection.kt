package fit.asta.health.subscription.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.subscription.remote.model.SubscriptionResponse
import fit.asta.health.subscription.remote.model.UserSubscribedPlanStatusType
import fit.asta.health.subscription.remote.model.getUserSubscribedPlanStatusType
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
            Column {
                when (userSubscribedPlan.status.getUserSubscribedPlanStatusType()) {
                    UserSubscribedPlanStatusType.ACTIVE -> {
                        AppIcon(imageVector = Icons.Default.CheckCircle)
                    }

                    UserSubscribedPlanStatusType.NOT_BOUGHT -> {
                        AppIcon(imageVector = Icons.Default.Close)
                    }

                    UserSubscribedPlanStatusType.INACTIVE -> {
                        AppIcon(imageVector = Icons.Default.Refresh)
                    }

                    UserSubscribedPlanStatusType.TEMPORARY_INACTIVE -> {
                        AppIcon(imageVector = Icons.Default.Warning)
                    }
                    UserSubscribedPlanStatusType.NOT_BOUGHT -> {
                        AppIcon(imageVector = Icons.Default.Warning)
                    }
                }
                TitleTexts.Level3(text = "Expiry: " + userSubscribedPlan.expBy)
            }
        }

    }
}