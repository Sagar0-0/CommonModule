package fit.asta.health.common.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fit.asta.health.main.Graph

fun NavGraphBuilder.webView() {
    composable(Graph.WebView.route + "/{url}") {
        val url = it.arguments?.getString("url") ?: ""
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    loadUrl(url)
                }
            }
        )
    }
}