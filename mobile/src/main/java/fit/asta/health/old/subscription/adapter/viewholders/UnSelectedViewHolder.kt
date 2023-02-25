package fit.asta.health.old.subscription.adapter.viewholders

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.card.MaterialCardView
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old.subscription.data.SubscriptionItem
import fit.asta.health.old.subscription.listner.SubPlanSelectionListener


class UnSelectedViewHolder(
    itemView: View,
    private val subPlanSelectionListener: SubPlanSelectionListener?,
    var notifyDataChange: () -> Unit
) : BaseViewHolder<SubscriptionItem>(itemView) {
    override fun bindData(content: SubscriptionItem) {

        itemView.findViewById<AppCompatTextView>(R.id.txtPaymentTitle).text = content.title
        itemView.findViewById<AppCompatTextView>(R.id.txtPaymentSubTitle).text = content.subTitle
        itemView.findViewById<AppCompatTextView>(R.id.txtPaymentDesc).text = content.description

        itemView.findViewById<MaterialCardView>(R.id.cardSubscriptionPlan).setOnClickListener {

            subPlanSelectionListener?.onSelectionUpdate(content.uid)
            notifyDataChange()
        }
    }
}