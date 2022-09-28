package fit.asta.health.navigation.home_old.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import fit.asta.health.R
import fit.asta.health.navigation.home_old.categories.adapter.listeners.OnCategoryClickListener
import fit.asta.health.navigation.home_old.categories.adapter.viewholder.HeaderViewHolder
import fit.asta.health.navigation.home_old.categories.data.CategoryData
import fit.asta.health.common.BaseAdapter
import fit.asta.health.common.BaseViewHolder


class CategoriesAdapter : BaseAdapter<CategoryData>() {

    private var onClickListener: OnCategoryClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CategoryData> {

        return HeaderViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_gallery_category_card, parent, false),
            onClickListener
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<CategoryData>, position: Int) {
        val itemHolder = holder as HeaderViewHolder
        itemHolder.bindData(items[position])
    }

    fun setAdapterClickListener(listener: OnCategoryClickListener) {
        onClickListener = listener
    }
}