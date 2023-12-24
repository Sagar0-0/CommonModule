package fit.asta.health.data.walking.data

data class Settings(
    val dailyGoal: Int = 7000,
    val stepLength: Int = 72,
    val height: Int = 188,
    val weight: Int = 70,
    val pace: Double = 1.0
)
