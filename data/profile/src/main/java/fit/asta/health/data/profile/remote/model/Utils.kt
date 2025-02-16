package fit.asta.health.data.profile.remote.model

const val BasicDetail_Screen_Name = "bscDtl"
const val Physique_Screen_Name = "phq"
const val Health_Screen_Name = "hlt"
const val Lifestyle_Screen_Name = "ls"
const val Diet_Screen_name = "diet"

const val Address_Field_Name = "adr"
const val DOB_Field_Name = "dob"
const val Mail_Field_Name = "mail"
const val Name_Field_Name = "name"
const val Phone_Field_Name = "ph"
const val Media_Field_Name = "media"
const val Age_Field_Name = "age"
const val Gender_Field_Name = "gen"
const val IsPregnant_Field_Name = "prg"
const val OnPeriod_Field_Name = "prd"
const val PregWeek_Field_Name = "pw"

const val MediaUrl_Field_Name = "url"
const val MediaMailUrl_Field_Name = "mailUrl"

const val Address_Inner_Field_Name = "adr1"
const val Country_Field_Name = "cnt"
const val City_Field_Name = "cty"
const val Pin_Field_Name = "pin"
const val Street_Field_Name = "st"

const val BodyType_Field_Name = "bdt"
const val Bmi_Field_Name = "bmi"
const val BmiUnit_Field_Name = "bmiUnit"
const val Height_Field_Name = "ht"
const val HeightUnit_Field_Name = "htUnit"
const val Weight_Field_Name = "wt"
const val WeightUnit_Field_Name = "wtUnit"

const val Medications_Field_Name = "med"
const val HealthTargets_Field_Name = "htg"
const val Ailments_Field_Name = "ail"
const val HealthHistory_Field_Name = "hh"
const val Injuries_Field_Name = "inj"
const val BodyParts_Field_Name = "bp"
const val Addictions_Field_Name = "add"
const val InjurySince_Field_Name = "is"

const val PhysicallyActive_Field_Name = "act"
const val WorkEnvironment_Field_Name = "we"
const val WorkStyle_Field_Name = "ws"
const val WorkHours_Field_Name = "whr"
const val CurrentActivities_Field_Name = "cat"
const val PreferredActivities_Field_Name = "pat"
const val LifestyleTargets_Field_Name = "lst"
const val WorkTime_Field_Name = "job"
const val SleepTime_Field_Name = "slp"

const val Preference_Field_Name = "pref"
const val NonVeg_Field_Name = "nv"
const val Allergies_Field_Name = "alg"
const val Cuisines_Field_Name = "cns"
const val Restrictions_Field_Name = "frs"

const val NON_VEG_QUERY_PARAM = "nv"
const val FOOD_QUERY_PARAM = "food"
const val CUISINES_QUERY_PARAM = "cu"

const val StartTime_Field_Name = "str"
const val EndTime_Field_Name = "end"
const val Hour_Field_Name = "hr"
const val Minute_Field_Name = "min"


typealias Gender = Int

enum class GenderTypes(val gender: Gender, val title: String) {
    MALE(1, "Male"),
    FEMALE(2, "Female"),
    OTHER(3, "Other")
}

fun Gender?.isMale(): Boolean {
    if (this == null) return false
    return this == GenderTypes.MALE.gender
}

fun Gender?.isFemale(): Boolean {
    if (this == null) return false
    return this == GenderTypes.FEMALE.gender
}

fun Gender?.isOther(): Boolean {
    if (this == null) return false
    return this == GenderTypes.OTHER.gender
}

fun Gender?.getGenderName(): String? {
    GenderTypes.entries.forEach {
        if (it.gender == this) return it.title
    }
    return null
}

typealias BooleanInt = Int

enum class BooleanIntTypes(val value: BooleanInt, val title: String) {
    YES(1, "Yes"),
    NO(0, "No")
}

fun BooleanInt?.isTrue(): Boolean {
    if (this == null) return false
    return this == BooleanIntTypes.YES.value
}

interface PhysiqueUnit {
    val title: String
    val value: Int
}

enum class HeightUnit(override val title: String, override val value: Int) : PhysiqueUnit {
    CM("cm", 1),
    INCH("in", 6);

    companion object {

        fun getName(value: Int): String {
            WeightUnit.entries.forEach { unit ->
                if (unit.value == value) return unit.title
            }
            return ""
        }

        fun indexOf(newValue: Int?): Int? {
            HeightUnit.entries.forEachIndexed { index, unit ->
                if (unit.value == newValue) return index
            }
            return null
        }

        fun getType(value: Int): HeightUnit {
            HeightUnit.entries.forEach {
                if (it.value == value) return it
            }
            return HeightUnit.CM
        }
    }

}

enum class BodyTypes(val value: Int) {

    Weak(1),
    Normal(2),
    Fat(3);

    companion object {
        fun getBodyType(value: Int): BodyTypes {
            BodyTypes.entries.forEach {
                if (it.value == value) return it
            }
            return Weak
        }

        fun indexOf(newValue: Int?): Int? {
            BodyTypes.entries.forEachIndexed { index, unit ->
                if (unit.value == newValue) return index
            }
            return null
        }
    }
}

enum class WorkStyles(val value: Int) {

    Sitting(1),
    Standing(2),
    Running(3);

    companion object {
        fun getType(value: Int?): WorkStyles? {
            if (value == null) return null
            WorkStyles.entries.forEach {
                if (it.value == value) return it
            }
            return Sitting
        }

        fun indexOf(newValue: Int?): Int? {
            WorkStyles.entries.forEachIndexed { index, unit ->
                if (unit.value == newValue) return index
            }
            return null
        }
    }
}

enum class WorkEnvironments(val value: Int) {

    First(1),
    Second(2);

    companion object {
        fun getType(value: Int?): WorkEnvironments? {
            if (value == null) return null
            WorkEnvironments.entries.forEach {
                if (it.value == value) return it
            }
            return First
        }

        fun indexOf(newValue: Int?): Int? {
            WorkEnvironments.entries.forEachIndexed { index, unit ->
                if (unit.value == newValue) return index
            }
            return null
        }
    }
}

enum class WeightUnit(override val title: String, override val value: Int) : PhysiqueUnit {
    KG("kg", 7),
    LB("lb", 8);

    companion object {
        fun getName(value: Int): String {
            WeightUnit.entries.forEach { unit ->
                if (unit.value == value) return unit.title
            }
            return ""
        }

        fun indexOf(newValue: Int?): Int? {
            WeightUnit.entries.forEachIndexed { index, unit ->
                if (unit.value == newValue) return index
            }
            return null
        }

        fun getType(value: Int): WeightUnit {
            WeightUnit.entries.forEach {
                if (it.value == value) return it
            }
            return KG
        }
    }
}

enum class PhysicallyActive(val title: String, val value: Int) {
    LOW("Low", 1),
    Moderate("Moderate", 2),
    VeryActive("Very Active", 3);

    companion object {
        fun indexOf(newValue: Int): Int {
            PhysicallyActive.entries.forEachIndexed { index, unit ->
                if (unit.value == newValue) return index
            }
            return -1
        }
    }
}

enum class BMIUnit(val value: Int) {
    BMI(19)
}