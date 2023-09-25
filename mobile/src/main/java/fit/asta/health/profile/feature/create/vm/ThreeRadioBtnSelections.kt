package fit.asta.health.profile.feature.create.vm


sealed class ThreeRadioBtnSelections {
    object First : ThreeRadioBtnSelections()
    object Second : ThreeRadioBtnSelections()
    object Third : ThreeRadioBtnSelections()
}

sealed class TwoRadioBtnSelections {
    object First : TwoRadioBtnSelections()
    object Second : TwoRadioBtnSelections()
}

sealed class ComposeIndex {
    object First : ComposeIndex()
    object Second : ComposeIndex()
    object Third : ComposeIndex()
}