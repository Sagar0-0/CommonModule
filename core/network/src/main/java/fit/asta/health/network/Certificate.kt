package fit.asta.health.network

data class Certificate(
    val hostName: String = "asta.care",
    //Certificate Hash codes (SHA-1/SHA-256)
    val pins: List<String> = listOf("21 99 06 DE DA 62 2E CB 0D 14 EA FB 90 F8 58 3C AB 0C E1 C0 62 18 50 B6 80 F4 94 D4 01 6F B2 27")
)