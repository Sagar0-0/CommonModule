package fit.asta.health.data.walking.domain.usecase

import fit.asta.health.data.walking.data.Settings
import fit.asta.health.data.walking.domain.model.toDay
import fit.asta.health.data.walking.domain.repository.DayRepository
import java.time.LocalDate

class SetDay(private val repository: DayRepository) {
    suspend operator fun invoke(setting: Settings, targetDistance: Float, targetDuration: Int) {
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