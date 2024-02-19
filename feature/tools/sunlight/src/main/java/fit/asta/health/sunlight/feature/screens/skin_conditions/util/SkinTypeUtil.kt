package fit.asta.health.sunlight.feature.screens.skin_conditions.util

object SkinTypeUtil {
    val skinTypeList = listOf(
        SkinSPF(
            "I. Always burns, doesn't tan",
            0
        ),
        SkinSPF(
            "II. Burns easily, tans poorly",
            1
        ),
        SkinSPF(
            "III. Tans after initial burn",
            2
        ),
        SkinSPF(
            "IV. Burns minimally, tans easily",
            3
        ),
        SkinSPF(
            "V. Rarely burns,tans darkly easily",
            4
        ),
        SkinSPF(
            "VI. Never burns, always tans darkly",
            5
        )
    )
}