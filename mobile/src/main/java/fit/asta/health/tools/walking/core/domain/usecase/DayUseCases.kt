package fit.asta.health.tools.walking.core.domain.usecase

data class DayUseCases(
    val getDay: GetDay,
    val setDay: SetDay,
    val incrementStepCount: IncrementStepCount,
    val incrementStepDuration: IncrementStepDuration,
    val changeStepsState: ActiveStateOfSteps,
    val getAllDayData: GetTodayData,
)