package fit.asta.health.firebase.model.service

interface CrashLogService {
    fun logNonFatalCrash(throwable: Throwable)
}
