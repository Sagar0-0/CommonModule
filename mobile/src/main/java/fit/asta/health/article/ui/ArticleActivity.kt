package fit.asta.health.article.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import fit.asta.health.R
import fit.asta.health.article.adapter.ArticleContentAdapter
import fit.asta.health.article.data.ArticleSupplier
import kotlinx.android.synthetic.main.article_activity.*


const val ARTICLE_URL = "Article_URL"

class ArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.article_activity)

        val toolbar: Toolbar = findViewById(R.id.articleToolbar)
        toolbar.setNavigationOnClickListener {

            onBackPressed()
        }

        val urlArticle = intent.getStringExtra(ARTICLE_URL)
        if (urlArticle != null)
            articleRecyclerView(urlArticle)
    }

    private fun articleRecyclerView(urlArticle: String) {

        articleRcView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        ArticleSupplier().fetchArticle(urlArticle)
            .observe(this, Observer { article ->

                progressArticle.hide()
                val adapter = ArticleContentAdapter()
                adapter.updateList(arrayListOf())
                articleRcView.adapter = adapter
            })
    }
}
