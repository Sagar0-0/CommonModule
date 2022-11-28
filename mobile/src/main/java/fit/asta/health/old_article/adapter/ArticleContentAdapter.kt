package fit.asta.health.old_article.adapter

import android.view.ViewGroup
import fit.asta.health.common.BaseAdapter
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old_article.data.ArticleContent
import fit.asta.health.old_article.viewholder.BaseViewHolderFactory
import javax.inject.Inject


class ArticleContentAdapter : BaseAdapter<ArticleContent>() {

    @Inject
    lateinit var viewHolderFactory: BaseViewHolderFactory

    override fun getItemViewType(position: Int): Int {
        return items[position].type.value
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ArticleContent> {
        return viewHolderFactory.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ArticleContent>, position: Int) {
        holder.bindData(items[position])
    }
}