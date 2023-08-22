package fit.asta.health.common.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

private const val WEBVIEW_GRAPH_ROUTE = "graph_webview"
fun NavController.navigateToWebView(url: String, navOptions: NavOptions? = null) {
    this.navigate("$WEBVIEW_GRAPH_ROUTE/url", navOptions)
}

fun NavGraphBuilder.webView() {
    composable("$WEBVIEW_GRAPH_ROUTE/{url}") {
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