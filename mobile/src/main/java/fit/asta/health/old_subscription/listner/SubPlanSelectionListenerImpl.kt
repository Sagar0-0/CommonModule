package fit.asta.health.old_subscription.listner

import fit.asta.health.old_subscription.viewmodel.SubscriptionViewModel

class SubPlanSelectionListenerImpl(val viewModel: SubscriptionViewModel) :
    SubPlanSelectionListener {
    override fun onSelectionUpdate(uId: String) {
        viewModel.updateSelection(uId)
    }
}