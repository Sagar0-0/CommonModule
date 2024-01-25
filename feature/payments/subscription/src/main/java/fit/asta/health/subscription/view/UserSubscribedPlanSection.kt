package fit.asta.health.subscription.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.subscription.remote.model.UserSubscribedPlan
import fit.asta.health.subscription.remote.model.UserSubscribedPlanStatusType

@Composable
fun UserSubscribedPlanSection(userSubscribedPlan: UserSubscribedPlan) {

    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            AppNetworkImage(
                model = getImgUrl(userSubscribedPlan.imageUrl),
                modifier = Modifier
                    .size(AppTheme.imageSize.level11)
                    .aspectRatio(ratio = AppTheme.aspectRatio.square)
            )
            TitleTexts.Level1(
                modifier = Modifier
                    .padding(AppTheme.spacing.level2),
                text = userSubscribedPlan.plan
            )
            Column {
                when (userSubscribedPlan.sts) {
                    UserSubscribedPlanStatusType.ACTIVE.code -> {
                        AppIcon(imageVector = Icons.Default.CheckCircle)
                    }

                    UserSubscribedPlanStatusType.NOT_BOUGHT.code -> {
                        AppIcon(imageVector = Icons.Default.Close)
                    }

                    UserSubscribedPlanStatusType.INACTIVE.code -> {
                        AppIcon(imageVector = Icons.Default.Refresh)
                    }

                    UserSubscribedPlanStatusType.TEMPORARY_INACTIVE.code -> {
                        AppIcon(imageVector = Icons.Default.Warning)
                    }
                }
                TitleTexts.Level3(text = "Expiry on: " + userSubscribedPlan.expBy)
            }
        }

    }
}