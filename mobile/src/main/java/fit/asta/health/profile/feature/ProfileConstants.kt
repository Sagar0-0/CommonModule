package fit.asta.health.profile.feature

object ProfileConstants {

    //Contact
    const val USER_IMG = "profile_img_before"
    const val NAME = "name"
    const val PHONE = "phone"
    const val EMAIL = "email"
    const val ADDRESS = "address"
    const val ID = "id"

    //Physique
    const val DOB = "dob"
    const val AGE = "age"
    const val WEIGHT = "weight"
    const val HEIGHT = "height"
    const val GENDER = "gender"
    const val IS_PREGNANT = "isPregnant"
    const val PREGNANCY_WEEK = "pregnancyWeek"
    const val BODY_TYPE = "bodyType"
    const val BMI = "bmi"

    //LifeStyle
    const val WAKEUPTIME = "wakeUpTime"
    const val BEDTIME = "bedTime"
    const val JSTARTTIME = "jStartTime"
    const val JENDTIME = "jEndTime"

    const val INJURIES_SINCE = "injuries_since"

    const val USER_SELECTION = "userSelection"
    const val PROFILE_DATA = "profileData"
    const val UID = "6309a9379af54f142c65fbfe"

    const val USER_FOOD_PREFERENCE = "userFoodPrep"

}


data class RadioButtonState(
    val optionSelected: Boolean = false,
)