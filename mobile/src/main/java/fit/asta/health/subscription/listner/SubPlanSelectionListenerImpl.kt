package fit.asta.health.subscription.listner

import fit.asta.health.subscription.viewmodel.SubscriptionViewModel

class SubPlanSelectionListenerImpl(val viewModel: SubscriptionViewModel) :
    SubPlanSelectionListener {
    override fun onSelectionUpdate(uId: String) {
        viewModel.updateSelection(uId)
    }
}