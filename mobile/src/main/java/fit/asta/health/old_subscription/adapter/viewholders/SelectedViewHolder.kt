package fit.asta.health.old_subscription.adapter.viewholders

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old_subscription.data.SubscriptionItem


class SelectedViewHolder(itemView: View) : BaseViewHolder<SubscriptionItem>(itemView) {
    override fun bindData(content: SubscriptionItem) {

        itemView.findViewById<AppCompatButton>(R.id.txtPaymentTag).text = content.tag
        itemView.findViewById<AppCompatTextView>(R.id.txtPaymentTitle).text = content.title
        itemView.findViewById<AppCompatTextView>(R.id.txtPaymentSubTitle).text = content.subTitle
        itemView.findViewById<AppCompatTextView>(R.id.txtPaymentDesc).text = content.description
    }
}