package fit.asta.health.settings

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import fit.asta.health.R
import kotlinx.android.synthetic.main.activity_web_view.*


const val WEB_URL = "WebUrl789"

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

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

            progressWebView.show()
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)

            progressWebView.hide()
        }
    }
}
