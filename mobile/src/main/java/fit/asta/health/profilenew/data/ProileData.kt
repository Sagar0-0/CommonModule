package fit.asta.health.profilenew.data

data class ProileData(
    val uid:String,
    val profileData:Map<String,Any>,
    val physic: Map<String,Any>,
    val health:Map<String,Any>,
    val lifestyle:Map<String,Any>,
    val diet:Map<String,Any>
)

data class MainProfile(
    val profileUrl: String,
    val name:String,
    val email:String,
    val phoneNumber:String,
    val dateOfBirth:String,
    val address:String
)

fun networkToString(s:String):String{
    return when(s){
        "age"->"age"
        "ht"->"height"
        "wt"->"weight"
        "bmi"->"BMI"
        "prg"->"pregnancy"
        "prgWk"->"Pregnancy Week"
        "bdyType"->"Body Type"
        else -> "gender"
    }
}


