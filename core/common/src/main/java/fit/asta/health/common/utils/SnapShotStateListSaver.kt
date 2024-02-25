package fit.asta.health.common.utils

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList

fun <T : Any> SnapshotStateListSaver(): Saver<SnapshotStateList<T>, *> = listSaver(
    save = { stateList ->
        if (stateList.isNotEmpty()) {
            val first = stateList.first()
            if (!canBeSaved(first)) {
                throw IllegalStateException("${first::class} cannot be saved. By default only types which can be stored in the Bundle class can be saved.")
            }
        }
        stateList.toList()
    },
    restore = { it.toMutableStateList() }
)