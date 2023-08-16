Created by [SAGAR MALHOTRA](sagar.0dev@gmail.com)

1. Define *feature*Route(navController) function as the main point of interaction with your feature screens from MainNavHost
2. NavController.navigateTo*feature*(***,navOptions ? = null) to navigate to that Route
3. Define Route name only in this same file and use that only

Add this feature route in MainNavHost

- Use UiState wrapper for your object state
- Use *feature*UiEvent callback method for any UiEvent
- Use *feature*Destinations sealed class for defining routes of subfeature screens
- Use only popBackStack() to navigate back from the screen
- If ur using nested NavHost then also work with nested NavController for navigating in *festure*Destinations

In Repo,
- Expose ResponseState wrapper for suspend functions
- use getResponseState{} fxn for wrapping any data calls
- Expose Flow<*> for observables with non-suspend functions
- Inject dispatcher and Use withContext(dispatcher) to have main safe suspend calls
- for thr work that needs to be surely done even when user leaves the screen, then use externalScope.launch{ //work// }.join()

In VM,
- Use toUiState() to map ResponseState to UiState
- Expose StateFlow objects
- if possible, use stateIn() to Map Flow<*> into stateFlow
- handle events in repo layer


In Unit Testing,
- Override BaseTest class, beforeEach and afterEach for viewmodel testing
- Use mockk,Junit5,turbine
