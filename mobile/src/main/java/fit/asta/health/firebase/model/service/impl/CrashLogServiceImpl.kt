package fit.asta.health.firebase.model.service.impl

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import fit.asta.health.firebase.model.service.CrashLogService
import javax.inject.Inject

class CrashLogServiceImpl @Inject constructor() : CrashLogService {
    override fun logNonFatalCrash(throwable: Throwable) =
        Firebase.crashlytics.recordException(throwable)
}
