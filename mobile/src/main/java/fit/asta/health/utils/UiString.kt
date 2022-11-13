package fit.asta.health.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiString {
    companion object {
        private const val EMPTY = ""
    }

    data class Dynamic(val value: String) : UiString()
    class Resource(@StringRes val id: Int, vararg val args: Any) : UiString()
    object Empty : UiString()

    @Composable
    fun asString(): String {
        return when (this) {
            is Dynamic -> value
            is Resource -> stringResource(id = id, formatArgs = args)
            is Empty -> EMPTY
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is Dynamic -> value
            is Resource -> context.getString(id, *args)
            Empty -> EMPTY
        }
    }
}