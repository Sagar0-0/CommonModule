package fit.asta.health.navigation.home.categories.adapter.viewholder

import android.net.Uri
import android.view.View
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.navigation.home.categories.adapter.listeners.OnCategoryClickListener
import fit.asta.health.navigation.home.categories.data.CategoryData
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl
import kotlinx.android.synthetic.main.gallery_category.view.*
import java.util.*


class HeaderViewHolder(
    viewItem: View,
    private val onClickListener: OnCategoryClickListener?
) : BaseViewHolder<CategoryData>(viewItem), View.OnClickListener {

    private var currentItem: CategoryData? = null

    init {
        itemView.cardCourseImage?.setOnClickListener(this)
    }

    override fun bindData(content: CategoryData) {

        currentItem = content

        itemView.galleryTitle.text = content.title.toUpperCase(Locale.getDefault())
        itemView.context.showImageByUrl(
            Uri.parse(getPublicStorageUrl(itemView.context, content.imgUrl)),
            itemView.categoryImage
        )
    }

    override fun onClick(view: View) {
        when (view) {
            itemView.cardCourseImage -> {
                onClickListener?.onCategoryClick(layoutPosition)
            }
        }
    }
}
