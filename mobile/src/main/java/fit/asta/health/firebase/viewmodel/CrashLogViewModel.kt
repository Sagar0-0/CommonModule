package fit.asta.health.firebase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.firebase.model.service.CrashLogService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


open class CrashLogViewModel(private val crashLogService: CrashLogService) : ViewModel() {
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    //SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
                crashLogService.logNonFatalCrash(throwable)
            },
            block = block
        )
}
