package fit.asta.health.subscription.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.payment.remote.model.OrderRequest
import fit.asta.health.subscription.remote.model.SubscriptionResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionPlansUi(
    state: UiState<SubscriptionResponse>,
    onBackPress: () -> Unit,
    onTryAgain: () -> Unit,
    onClick: (orderRequest: OrderRequest) -> Unit
) {
    Scaffold(
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
                    LoadingAnimation()
                }
            }

            is UiState.ErrorMessage -> {
                Text(state.resId.toStringFromResId())
            }

            is UiState.Success -> {
                Column(modifier = Modifier.padding(paddingValues)) {
                    if (state.data.userSubscribedPlan != null) {
                        if (state.data.userSubscribedPlan!!.sts) {
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
                AppErrorScreen(
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
    Text(text = data.subscriptionPlans.categories[data.userSubscribedPlan!!.type.toInt() - 1].ttl)
}


