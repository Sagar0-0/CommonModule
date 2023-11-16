Created by [SAGAR MALHOTRA](sagar.0dev@gmail.com)

1. Define *feature*Route(navController) function as the main point of interaction with your feature screens from MainNavHost
2. NavController.navigateTo*feature*(***,navOptions ? = null) to navigate to that Route
3. Define Route name only in this same file and use that only

Add this feature route in MainNavHost

- Use UiState wrapper for your object state
- Use *feature*UiEvent callback method for any UiEvent
- Use *feature*Destinations sealed class for defining routes of subfeature screens
- Use only popBackStack() to navigate back from the screen
- If ur using nested NavHost then also work with nested NavController for navigating in *festure*
  Destinations

In Api,

- Use Response<> wrapper(from common main) to wrap your data for api Status and data

In Repo,

- Expose ResponseState wrapper for suspend functions
- use getApiResponseState{} function for wrapping any data calls
- use ApiErrorHandler class to override the functions and add you own status code Handling(must
  override super methods in else block).
- Expose Flow<*> for observables with non-suspend functions
- Inject dispatcher and Use withContext(dispatcher) to have main safe suspend calls
- for thr work that needs to be surely done even when user leaves the screen, then use
  externalScope.launch{ //work// }.join()

In VM,

- Use toUiState() to map ResponseState to UiState
- Expose StateFlow objects
- If possible, use stateIn() to Map Flow<*> into stateFlow
- handle events in repo layer

In Unit Testing,

- Override BaseTest class, beforeEach and afterEach for viewmodel testing
- Use mockk,Junit5,turbine

For UI Creation:

1. Valid and understandable names, no use of any short forms.
2. Use values from design system
3. Dynamic objects should be present in params, get clarity before implementation
4. Create a separate composable for preview
5. Only pass objects, states, callbacks in params of composable functions.
6. Larger and reusable functions should be present in separate files.
7. Follow Single responsibility principle, understand it properly before starting.
8. While calling a function, give parameter names if more than 2 exists.
9. Use string from resource file instead of hard coding.
10. While creating a function, follow this order... Mandatory params -> Modifier -> other optional
    param -> one mandatory callback function(if any).



