package fit.asta.health.feature.scheduler.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

class ToolsStateManager @Inject constructor() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

}