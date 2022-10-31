package fit.asta.health.settings

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import com.google.android.material.appbar.MaterialToolbar
import fit.asta.health.R


const val WEB_URL = "WebUrl789"

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_activity)

        val toolbarWebView = findViewById<MaterialToolbar>(R.id.toolbarWebView)
        setSupportActionBar(toolbarWebView)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbarWebView?.setNavigationOnClickListener {

            onBackPressed()
        }

        val url = intent.getStringExtra(WEB_URL)
        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = SubWebClient()
        webView.loadUrl(url!!)
    }

    inner class SubWebClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

            findViewById<ContentLoadingProgressBar>(R.id.progressWebView).show()
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)

            findViewById<ContentLoadingProgressBar>(R.id.progressWebView).hide()
        }
    }
}
