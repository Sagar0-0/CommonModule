package fit.asta.health.navigation.tools.ui.viewmodel

import fit.asta.health.navigation.tools.data.model.domain.ToolsHomeRes
import java.io.IOException


sealed class HomeState {
    object Loading : HomeState()
    class Success(val toolsHome: ToolsHomeRes.ToolsHome) : HomeState()
    class Error(val error: Throwable) : HomeState()
    class NetworkError(val error: IOException) : HomeState()
}