package fit.asta.health.navigation.today.adapter

import android.view.ViewGroup
import fit.asta.health.common.BaseAdapter
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.navigation.today.adapter.listeners.OnPlanClickListener
import fit.asta.health.navigation.today.data.TodayPlanItemData
import javax.inject.Inject


class TodayPlanAdapter : BaseAdapter<TodayPlanItemData>() {
    @Inject
    lateinit var viewHolderFactory: TodayBaseViewHolderFactory
    private var onClickListener: OnPlanClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<TodayPlanItemData> {
        return viewHolderFactory.create(parent, viewType, onClickListener)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<TodayPlanItemData>, position: Int) {
        holder.bindData(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type.value
    }

    fun setAdapterClickListener(listener: OnPlanClickListener){
        onClickListener = listener
    }
}
