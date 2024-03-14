package fit.asta.health.data.walking.usecase

data class DayUseCases(
    val getDay: GetDay,
    val setDay: SetDay,
    val incrementStepCount: IncrementStepCount,
    val incrementStepDuration: IncrementStepDuration,
    val changeStepsState: ActiveStateOfSteps,
    val getAllDayData: GetTodayData,
    val getDailyData: GetDailyData,
    val incrementStepsInDaily: IncrementStepsInDaily
)