package fit.asta.health.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.widget.ContentLoadingProgressBar
import com.google.android.material.appbar.MaterialToolbar
import fit.asta.health.R

class WebViewActivity : AppCompatActivity() {

    companion object {

        private var webUrl by mutableStateOf("")
        fun launch(context: Context, url: String) {
            webUrl = url
            Intent(context, WebViewActivity::class.java)
                .apply {
                    context.startActivity(this)
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_activity)

        val toolbarWebView = findViewById<MaterialToolbar>(R.id.toolbarWebView)
        setSupportActionBar(toolbarWebView)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbarWebView?.setNavigationOnClickListener {
            finish()
        }
        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = SubWebClient()
        webView.loadUrl(webUrl)
    }

    inner class SubWebClient : WebViewClient() {

        @Deprecated("Deprecated in Java")
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
