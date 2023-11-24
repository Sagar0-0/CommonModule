package fit.asta.health.tools.walking.viewmodel

import fit.asta.health.tools.walking.core.domain.model.WalkingTool

sealed class WalkingState {
    object Loading : WalkingState()
    class Success(val walkingTool: WalkingTool) : WalkingState()
    class Error(val error: Throwable) : WalkingState()
}