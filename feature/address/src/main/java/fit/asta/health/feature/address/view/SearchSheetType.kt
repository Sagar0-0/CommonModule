package fit.asta.health.feature.address.view

sealed interface SearchSheetType {
    data object FromSavedAddress : SearchSheetType
    data object FromMapScreen : SearchSheetType
}
