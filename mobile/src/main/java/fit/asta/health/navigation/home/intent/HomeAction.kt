package fit.asta.health.navigation.home.intent

import fit.asta.health.navigation.home.model.domain.ToolsHome


sealed class HomeAction {

    class LoadHomeData(val toolsHome: ToolsHome) : HomeAction()
    object Empty : HomeAction()
    class Error(val error: Throwable) : HomeAction()
}