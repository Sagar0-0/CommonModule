package fit.asta.health.feature.address.view

import fit.asta.health.data.address.remote.modal.MyAddress

sealed interface SearchSheetUiEvent {
    data object Close : SearchSheetUiEvent
    class OnResultClick(val myAddress: MyAddress) : SearchSheetUiEvent
    class Search(val query: String) : SearchSheetUiEvent
    data object ClearSearchResponse : SearchSheetUiEvent
}