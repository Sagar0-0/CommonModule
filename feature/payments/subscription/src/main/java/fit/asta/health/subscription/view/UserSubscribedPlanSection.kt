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
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.subscription.remote.model.UserSubscribedPlan
import fit.asta.health.subscription.remote.model.UserSubscribedPlanStatusType

@Composable
fun UserSubscribedPlanSection(
    userSubscribedPlan: UserSubscribedPlan,
    onClick: (categoryId: String, productId: String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = AppTheme.spacing.level2)
            .padding(horizontal = AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
    ) {
        HeadingTexts.Level1(
            text = "Your plan:"
        )
        AppCard(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onClick(userSubscribedPlan.categoryId, userSubscribedPlan.productId)
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                AppNetworkImage(
                    model = getImgUrl(userSubscribedPlan.imageUrl),
                    modifier = Modifier
                        .padding(end = AppTheme.spacing.level1)
                        .size(AppTheme.imageSize.level11)
                        .aspectRatio(ratio = AppTheme.aspectRatio.square)
                )
                Column(
                    modifier = Modifier
                        .padding(AppTheme.spacing.level1),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TitleTexts.Level1(
                            maxLines = 1,
                            modifier = Modifier
                                .weight(1f),
                            text = userSubscribedPlan.ttl
                        )
                        AppIcon(
                            imageVector = when (userSubscribedPlan.sts) {

                                UserSubscribedPlanStatusType.ACTIVE.code -> {
                                    Icons.Default.CheckCircle
                                }

                                UserSubscribedPlanStatusType.NOT_BOUGHT.code -> {
                                    Icons.Default.Close
                                }

                                UserSubscribedPlanStatusType.INACTIVE.code -> {
                                    Icons.Default.Refresh
                                }

                                else -> {
                                    Icons.Default.Warning
                                }
                            }
                        )
                    }

                    BodyTexts.Level3(text = "Expiry on: " + userSubscribedPlan.expBy)
                }
            }

        }
    }

}