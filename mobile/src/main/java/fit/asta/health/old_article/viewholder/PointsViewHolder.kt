package fit.asta.health.old_article.viewholder

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old_article.adapter.ArticlePointsAdapter
import fit.asta.health.old_article.data.ArticleContent
import kotlinx.android.synthetic.main.article_points.view.*

class PointsViewHolder(itemView: View) : BaseViewHolder<ArticleContent>(itemView) {

    private val sharedPool = RecyclerView.RecycledViewPool()

    private fun pointsRecyclerView(itemView: View, content: ArticleContent) {

        itemView.articlePointsRcView.apply {

            layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            adapter = ArticlePointsAdapter(
                itemView.context, content.metaData,
                content.points.type, content.points.topics
            )
            this.setRecycledViewPool(sharedPool)
        }
    }

    override fun bindData(content: ArticleContent) {
        itemView.apply {
            articlePointsTitle.text = content.title
            articlePointsText.visibility = View.GONE
            articlePointsText.visibility = View.VISIBLE
            articlePointsText.text = content.text
        }

        pointsRecyclerView(itemView, content)
    }
}