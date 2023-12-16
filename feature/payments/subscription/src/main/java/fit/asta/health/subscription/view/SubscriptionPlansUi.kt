package fit.asta.health.subscription.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.payment.remote.model.OrderRequest
import fit.asta.health.subscription.remote.model.SubscriptionResponse
import fit.asta.health.subscription.remote.model.UserSubscribedPlanStatusType
import fit.asta.health.subscription.remote.model.getUserSubscribedPlanStatusType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionPlansUi(
    state: UiState<SubscriptionResponse>,
    onBackPress: () -> Unit,
    onTryAgain: () -> Unit,
    onClick: (orderRequest: OrderRequest) -> Unit
) {
    AppScaffold(
        topBar = {
            AppTopBar(title = "Subscription", onBack = onBackPress)
        }
    ) { paddingValues ->
        when (state) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    AppDotTypingAnimation()
                }
            }

            is UiState.ErrorMessage -> {
                TitleTexts.Level2(text = state.resId.toStringFromResId())
            }

            is UiState.Success -> {
                Column(modifier = Modifier.padding(paddingValues)) {
                    if (state.data.userSubscribedPlan != null) {
                        if (state.data.userSubscribedPlan!!.status.getUserSubscribedPlanStatusType() == UserSubscribedPlanStatusType.ACTIVE) {
                            ShowUserActivePlan(state.data)
                        } else {
                            ShowUserExpiredPlan(state.data)
                        }
                    }
                    SubPlansPager(
                        subscriptionPlans = state.data.subscriptionPlans,
                        onClick = onClick
                    )
                }
            }

            is UiState.NoInternet -> {
                AppInternetErrorDialog(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    onTryAgain()
                }
            }

            else -> {}
        }
    }
}

@Composable
fun ShowUserExpiredPlan(data: SubscriptionResponse) {

}

@Composable
fun PendingPlanUI(data: SubscriptionResponse) {

}

@Composable
private fun ShowUserActivePlan(data: SubscriptionResponse) {
    TitleTexts.Level2(text = data.subscriptionPlans.subscriptionPlanTypes[data.userSubscribedPlan!!.type.toInt() - 1].planName)
}


