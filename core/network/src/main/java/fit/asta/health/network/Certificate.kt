package fit.asta.health.network

data class Certificate(
    val hostName: String = "asta.care",
    //Certificate Hash codes (SHA-1/SHA-256)
    val pins: List<String> = listOf("SHA-256/219906DEDA622ECB0D14EAFB90F8583CAB0CE1C0621850B680F494D4016FB227")
)