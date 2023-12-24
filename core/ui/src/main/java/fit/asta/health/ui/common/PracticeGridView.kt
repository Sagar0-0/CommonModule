package fit.asta.health.ui.common

data class PracticeGridView(
    val cardTitle: String,
    val cardImg: Int,
    val cardValue: String,
    val onClick: () -> Unit = {},
)