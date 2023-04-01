package fit.asta.health.profile.viewmodel

object ProfileConstants {

    const val DOB = "dob"
    const val EMAIL = "email"
    const val NAME = "name"
    const val PHONE = "phone"

    const val USER_IMG = "profile_img_before"

    const val AGE = "age"
    const val BODY_TYPE = "bodyType"
    const val WEIGHT = "weight"
    const val GENDER = "gender"
    const val HEIGHT = "height"
    const val IS_PREGNANT = "isPregnant"
    const val PREGNANCY_WEEK = "pregnancyWeek"

    const val INJURIES_SINCE = "injuries_since"

    const val USER_SELECTION = "userSelection"
    const val PROFILE_DATA = "profileData"
    const val UID = "6309a9379af54f142c65fbfe"

    const val USER_FOOD_PREFERENCE = "userFoodPrep"

}


data class RadioButtonState(
    val optionSelected: Boolean = false,
)