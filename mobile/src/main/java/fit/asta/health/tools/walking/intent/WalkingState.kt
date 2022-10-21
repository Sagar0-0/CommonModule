package fit.asta.health.tools.walking.intent

import fit.asta.health.tools.walking.model.domain.WalkingTool

sealed class WalkingState {
    object Loading : WalkingState()
    class Success(val walkingTool: WalkingTool) : WalkingState()
    class Error(val error: Throwable) : WalkingState()
}