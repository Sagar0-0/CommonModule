package fit.asta.health.subscription.adapter.viewholders

import android.view.View
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.subscription.data.SubscriptionItem
import kotlinx.android.synthetic.main.subscription_card_selected.view.*

class SelectedViewHolder(itemView: View) : BaseViewHolder<SubscriptionItem>(itemView) {
    override fun bindData(content: SubscriptionItem) {

        itemView.txtPaymentTag.text = content.tag
        itemView.txtPaymentTitle.text = content.title
        itemView.txtPaymentSubTitle.text = content.subTitle
        itemView.txtPaymentDesc.text = content.description
    }
}