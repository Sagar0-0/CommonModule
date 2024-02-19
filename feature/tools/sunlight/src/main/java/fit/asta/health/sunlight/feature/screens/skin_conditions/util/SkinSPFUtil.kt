package fit.asta.health.sunlight.feature.screens.skin_conditions.util

object SkinSPFUtil {
    val spfList = listOf(
        SkinSPF(
            "SPF 15",
            0
        ),
        SkinSPF(
            "SPF 25",
            1
        ),
        SkinSPF(
            "SPF 35",
            2
        ),
        SkinSPF(
            "SPF 40",
            3
        ),
        SkinSPF(
            "SPF 45",
            4
        ),
        SkinSPF(
            "SPF 50",
            5
        ),
        SkinSPF(
            "None",
            6
        ),

        )
}

data class SkinSPF(
    val title: String,
    val id: Int
)