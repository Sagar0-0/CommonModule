package fit.asta.health.firebase.model.service

interface ConfigService {
    suspend fun fetchConfiguration(): Boolean
    val isShowTaskEditButtonConfig: Boolean
}
