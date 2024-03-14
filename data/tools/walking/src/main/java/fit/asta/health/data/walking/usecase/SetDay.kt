package fit.asta.health.data.walking.usecase

import fit.asta.health.data.walking.local.model.Settings
import fit.asta.health.data.walking.local.model.toDay
import fit.asta.health.data.walking.repo.DayRepository
import java.time.LocalDate

class SetDay(private val repository: DayRepository) {
    suspend operator fun invoke(setting: Settings, targetDistance: Float, targetDuration: Float) {
        repository.upsertDay(
            setting.toDay(
                startupTime = System.currentTimeMillis(),
                date = LocalDate.now(),
                targetDistance = targetDistance,
                targetDuration = targetDuration
            )
        )
    }
}