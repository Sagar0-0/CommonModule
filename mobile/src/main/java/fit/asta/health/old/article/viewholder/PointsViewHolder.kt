package fit.asta.health.old.article.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old.article.adapter.ArticlePointsAdapter
import fit.asta.health.old.article.data.ArticleContent


class PointsViewHolder(itemView: View) : BaseViewHolder<ArticleContent>(itemView) {

    private val sharedPool = RecyclerView.RecycledViewPool()

    private fun pointsRecyclerView(itemView: View, content: ArticleContent) {

        val articlePointsRcView = itemView.findViewById<RecyclerView>(R.id.articlePointsRcView)
        articlePointsRcView.apply {

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
            findViewById<AppCompatTextView>(R.id.articlePointsTitle).text = content.title
            val articlePointsText = findViewById<AppCompatTextView>(R.id.articlePointsText)
            articlePointsText.visibility = View.GONE
            articlePointsText.visibility = View.VISIBLE
            articlePointsText.text = content.text
        }

        pointsRecyclerView(itemView, content)
    }
}