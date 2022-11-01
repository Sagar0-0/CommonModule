package fit.asta.health.old_article.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.R
import fit.asta.health.old_article.adapter.ArticleContentAdapter
import fit.asta.health.old_article.data.ArticleSupplier


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

        val articleRcView = findViewById<RecyclerView>(R.id.articleRcView)
        articleRcView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        ArticleSupplier().fetchArticle(urlArticle)
            .observe(this, Observer {

                findViewById<ContentLoadingProgressBar>(R.id.progressArticle).hide()
                val adapter = ArticleContentAdapter()
                adapter.updateList(arrayListOf())
                articleRcView.adapter = adapter
            })
    }
}
