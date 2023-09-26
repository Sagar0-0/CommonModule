package fit.asta.health.feature.profile.create.vm


sealed class ThreeRadioBtnSelections {
    data object First : ThreeRadioBtnSelections()
    data object Second : ThreeRadioBtnSelections()
    data object Third : ThreeRadioBtnSelections()
}

sealed class TwoRadioBtnSelections {
    data object First : TwoRadioBtnSelections()
    data object Second : TwoRadioBtnSelections()
}

sealed class ComposeIndex {
    data object First : ComposeIndex()
    data object Second : ComposeIndex()
    data object Third : ComposeIndex()
}