package fit.asta.health.old_subscription.adapter.viewholders

import android.view.View
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old_subscription.data.SubscriptionItem
import fit.asta.health.old_subscription.listner.SubPlanSelectionListener
import kotlinx.android.synthetic.main.subscription_card_unselected.view.*

class UnSelectedViewHolder(
    itemView: View,
    private val subPlanSelectionListener: SubPlanSelectionListener?,
    var notifyDataChange: () -> Unit
) : BaseViewHolder<SubscriptionItem>(itemView) {
    override fun bindData(content: SubscriptionItem) {

        itemView.txtPaymentTitle.text = content.title
        itemView.txtPaymentSubTitle.text = content.subTitle
        itemView.txtPaymentDesc.text = content.description



        itemView.cardSubscriptionPlan.setOnClickListener {


            subPlanSelectionListener?.onSelectionUpdate(content.uid)
            notifyDataChange()
        }
    }
}