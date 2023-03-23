package fit.asta.health.profile.model.domain

sealed class ThreeToggleSelections {
    object First : ThreeToggleSelections()
    object Second : ThreeToggleSelections()
    object Third : ThreeToggleSelections()
}

sealed class TwoToggleSelections {
    object First : TwoToggleSelections()
    object Second : TwoToggleSelections()
}