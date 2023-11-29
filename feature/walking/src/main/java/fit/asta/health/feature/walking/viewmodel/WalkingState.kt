package fit.asta.health.feature.walking.viewmodel

import fit.asta.health.data.walking.domain.model.WalkingTool

sealed class WalkingState {
    data object Loading : WalkingState()
    class Success(val walkingTool: WalkingTool) : WalkingState()
    class Error(val error: Throwable) : WalkingState()
}