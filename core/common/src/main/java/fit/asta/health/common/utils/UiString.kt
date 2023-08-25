package fit.asta.health.common.utils

import android.content.Context
import android.os.Parcelable
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import kotlinx.parcelize.Parcelize


@Parcelize
sealed class UiString : Parcelable {
    companion object {
        private const val EMPTY = ""
    }

    data class Dynamic(val value: String) : UiString()
    class Resource(@StringRes val id: Int, vararg val args: String) : UiString()
    data object Empty : UiString()

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


@Parcelize
sealed class UiInt : Parcelable {

    companion object {
        private const val EMPTY = 0
    }

    data class Dynamic(val value: Int) : UiInt()
    class Resource(@IntegerRes val id: Int, vararg val args: Int) : UiInt()
    object Empty : UiInt()

    @Composable
    fun asInt(): Int {
        return when (this) {
            is Dynamic -> value
            is Resource -> integerResource(id = id)
            is Empty -> EMPTY
        }
    }

}