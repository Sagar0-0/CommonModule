package fit.asta.health.article.adapter

import android.view.ViewGroup
import fit.asta.health.article.data.ArticleContent
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.article.viewholder.BaseViewHolderFactory
import fit.asta.health.common.BaseAdapter
import org.koin.core.KoinComponent
import org.koin.core.inject


class ArticleContentAdapter: BaseAdapter<ArticleContent>(), KoinComponent {

    private val viewHolderFactory: BaseViewHolderFactory by inject()

    override fun getItemViewType(position: Int): Int {
        return items[position].type.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ArticleContent> {
        return viewHolderFactory.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ArticleContent>, position: Int) {
        holder.bindData(items[position])
    }
}