package fit.asta.health.navigation.home.viewmodel

import fit.asta.health.navigation.home.model.domain.ToolsHomeRes
import java.io.IOException


sealed class HomeState {
    object Loading : HomeState()
    class Success(val toolsHome: ToolsHomeRes.ToolsHome) : HomeState()
    class Error(val error: Throwable) : HomeState()
    class NetworkError(val error: IOException) : HomeState()
}