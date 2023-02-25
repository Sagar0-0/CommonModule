package fit.asta.health.old.subscription.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old.subscription.adapter.viewholders.SelectedViewHolder
import fit.asta.health.old.subscription.adapter.viewholders.UnSelectedViewHolder
import fit.asta.health.old.subscription.data.SubscriptionItem
import fit.asta.health.old.subscription.data.SubscriptionItemType
import fit.asta.health.old.subscription.listner.SubPlanSelectionListener

class SubscriptionViewHolderFactory {

    fun create(
        parent: ViewGroup,
        viewType: Int,
        subPlanSelectionListener: SubPlanSelectionListener?,
        notifyDataChange: () -> Unit
    ): BaseViewHolder<SubscriptionItem> {

        return when (SubscriptionItemType.valueOf(viewType)) {
            SubscriptionItemType.SelectedSubscription -> {
                SelectedViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.subscription_card_selected, parent, false)
                )
            }
            SubscriptionItemType.UnselectedSubscription -> {
                UnSelectedViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.subscription_card_unselected, parent, false),
                    subPlanSelectionListener,
                    notifyDataChange
                )
            }
        }
    }
}