package fit.asta.health.navigation.home.viewmodel

import fit.asta.health.navigation.home.model.domain.ToolsHomeRes


sealed class HomeState {
    object Loading : HomeState()
    class Success(val toolsHome: ToolsHomeRes.ToolsHome) : HomeState()
    class Error(val error: Throwable) : HomeState()
}