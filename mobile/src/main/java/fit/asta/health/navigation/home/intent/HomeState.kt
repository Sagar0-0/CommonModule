package fit.asta.health.navigation.home.intent

import fit.asta.health.navigation.home.model.domain.ToolsHome


sealed class HomeState {
    object Loading : HomeState()
    class Success(val toolsHome: ToolsHome) : HomeState()
    class Error(val error: Throwable) : HomeState()
}