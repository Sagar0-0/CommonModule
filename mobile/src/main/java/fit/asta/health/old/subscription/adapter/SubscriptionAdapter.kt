package fit.asta.health.old.subscription.adapter

import android.view.ViewGroup
import fit.asta.health.common.BaseAdapter
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old.subscription.data.SubscriptionItem
import fit.asta.health.old.subscription.listner.SubPlanSelectionListener
import javax.inject.Inject


class SubscriptionAdapter : BaseAdapter<SubscriptionItem>() {

    @Inject
    lateinit var viewHolderFactory: SubscriptionViewHolderFactory
    private var changeSelectionListener: SubPlanSelectionListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SubscriptionItem> {
        return viewHolderFactory.create(
            parent,
            viewType,
            changeSelectionListener
        ) { notifyDataSetChanged() }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<SubscriptionItem>, position: Int) {
        holder.bindData(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].selected.value
    }

    fun setListener(selectionListener: SubPlanSelectionListener) {
        changeSelectionListener = selectionListener
    }

}