package fit.asta.health.navigation.home.intent

sealed class HomeStateEvent {

    data class GetHomeEvent(val id: Int): HomeStateEvent()
}