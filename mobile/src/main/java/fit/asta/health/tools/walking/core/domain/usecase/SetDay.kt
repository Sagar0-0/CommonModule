package fit.asta.health.tools.walking.core.domain.usecase

import fit.asta.health.tools.walking.core.data.Settings
import fit.asta.health.tools.walking.core.domain.model.toDay
import fit.asta.health.tools.walking.core.domain.repository.DayRepository
import java.time.LocalDate

class SetDay(private val repository: DayRepository) {
    suspend operator fun invoke(setting: Settings) {
        repository.upsertDay(
            setting.toDay(
                startupTime = System.currentTimeMillis(),
                date = LocalDate.now()
            )
        )
    }
}