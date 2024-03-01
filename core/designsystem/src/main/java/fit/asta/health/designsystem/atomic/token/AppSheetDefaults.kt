@file:OptIn(ExperimentalMaterial3Api::class)

package fit.asta.health.designsystem.atomic.token

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue



/**
 * Possible values of [SheetState] used in [AppBottomSheet].
 */
@OptIn(ExperimentalMaterial3Api::class)
object AppSheetValue {
    /**
     * The sheet is not visible.
     */
    val Hidden = SheetValue.Hidden

    /**
     * The sheet is visible at full height.
     */
    val Expanded = SheetValue.Expanded

    /**
     * The sheet is partially visible.
     */
    val PartiallyExpanded = SheetValue.PartiallyExpanded
}

