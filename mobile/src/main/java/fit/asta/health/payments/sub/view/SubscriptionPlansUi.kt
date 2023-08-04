package fit.asta.health.payments.sub.view

import android.util.Log
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
import fit.asta.health.common.ui.components.generic.AppErrorScreen
import fit.asta.health.common.ui.components.generic.AppTopBar
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payments.pay.model.OrderRequest
import fit.asta.health.payments.sub.model.SubscriptionResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionPlansUi(
    state: ResponseState<SubscriptionResponse>,
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
            is ResponseState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingAnimation()
                }
            }

            is ResponseState.Error -> {
                Log.e("Sub", "SubScreen: ${state.error}")
                AppErrorScreen(
                    modifier = Modifier.padding(paddingValues),
                    desc = "Something went wrong!",
                    isInternetError = false
                ) {
                    onTryAgain()
                }
            }

            is ResponseState.Success -> {
                Column(modifier = Modifier.padding(paddingValues)) {
                    if (state.data.data.pendingPlan != null) {
                        PendingPlanUI(state.data.data)
                    } else if (state.data.data.userSubscribedPlan != null) {
                        if (state.data.data.userSubscribedPlan.sts) {
                            ShowUserActivePlan(state.data.data)
                        } else {
                            ShowUserExpiredPlan(state.data.data)
                        }
                    }
                    SubPlansPager(
                        subscriptionPlans = state.data.data.subscriptionPlans,
                        onClick = onClick
                    )
                }
            }

            else -> {}
        }
    }
}

@Composable
fun ShowUserExpiredPlan(data: SubscriptionResponse.Data) {

}

@Composable
fun PendingPlanUI(data: SubscriptionResponse.Data) {

}

@Composable
private fun ShowUserActivePlan(data: SubscriptionResponse.Data) {
    Text(text = data.subscriptionPlans.categories[data.userSubscribedPlan!!.type.toInt() - 1].ttl)
}


