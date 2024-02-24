package fit.asta.health.data.profile.remote.model

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfileResponse(
    @SerializedName("uid") val uid: String = "",
    @SerializedName("id") val id: String = "",
    @SerializedName(BasicDetail_Screen_Name) val basicDetail: BasicDetail = BasicDetail(),
    @SerializedName(Physique_Screen_Name) val physique: Physique = Physique(),
    @SerializedName(Health_Screen_Name) val health: Health = Health(),
    @SerializedName(Lifestyle_Screen_Name) val lifeStyle: LifeStyle = LifeStyle(),
    @SerializedName(Diet_Screen_name) val diet: Diet = Diet(),
) : Parcelable

@Parcelize
data class BasicDetail(
    @SerializedName(Address_Field_Name) val userProfileAddress: UserProfileAddress? = null,
    @SerializedName(DOB_Field_Name) val dob: String? = null,
    @SerializedName(Mail_Field_Name) val email: String = "",
    @SerializedName(Name_Field_Name) var name: String = "",
    @SerializedName(Phone_Field_Name) val phoneNumber: String = "",
    @SerializedName(Media_Field_Name) val media: ProfileMedia = ProfileMedia(),
    @SerializedName(Age_Field_Name) val age: Int? = 0,
    @SerializedName(Gender_Field_Name) val gender: Gender? = 0,
    @SerializedName(IsPregnant_Field_Name) val isPregnant: BooleanInt? = 0,
    @SerializedName(OnPeriod_Field_Name) val onPeriod: BooleanInt? = 0,
    @SerializedName(PeriodWeek_Field_Name) val pregnancyWeek: Int? = 0,
) : Parcelable

@Parcelize
data class ProfileMedia(
    @SerializedName(MediaUrl_Field_Name) var url: String = "",
    @SerializedName(MediaMailUrl_Field_Name) var mailUrl: String = "",
    var localUri: Uri? = null
) : Parcelable

@Parcelize
data class UserProfileAddress(
    @SerializedName(Address_Inner_Field_Name) val address: String = "Address Line 1",
    @SerializedName(Country_Field_Name) val country: String = "India",
    @SerializedName(City_Field_Name) val city: String = "Noida",
    @SerializedName(Pin_Field_Name) val pin: String = "123456",
    @SerializedName(Street_Field_Name) val street: String = "Some Street",
) : Parcelable {
    override fun toString(): String {
        return "$address, $street, $city, $country"
    }
}

@Parcelize
data class Physique(
    @SerializedName(BodyType_Field_Name) val bodyType: Int? = 0,
    @SerializedName(Bmi_Field_Name) val bmi: Float? = 0f,
    @SerializedName(BmiUnit_Field_Name) val bmiUnit: Int? = 0,
    @SerializedName(Height_Field_Name) val height: Float? = 0f,
    @SerializedName(HeightUnit_Field_Name) val heightUnit: Int? = 0,
    @SerializedName(Weight_Field_Name) val weight: Float? = 0f,
    @SerializedName(WeightUnit_Field_Name) val weightUnit: Int? = 0,
) : Parcelable

@Parcelize
data class Health(
    @SerializedName(Medications_Field_Name) val medications: List<UserProperties>? = null,
    @SerializedName(Targets_Field_Name) val targets: List<UserProperties>? = null,
    @SerializedName(Ailments_Field_Name) val ailments: List<UserProperties>? = null,
    @SerializedName(HealthHistory_Field_Name) val healthHistory: List<UserProperties>? = null,
    @SerializedName(Injuries_Field_Name) val injuries: List<UserProperties>? = null,
    @SerializedName(BodyParts_Field_Name) val bodyPart: List<UserProperties>? = null,
    @SerializedName(Addictions_Field_Name) val addiction: List<UserProperties>? = null,
    @SerializedName(InjurySince_Field_Name) val injurySince: Int? = 0,
) : Parcelable

@Parcelize
data class LifeStyle(
    @SerializedName(PhysicallyActive_Field_Name) var physicalActive: Int? = null,
    @SerializedName(WorkEnvironment_Field_Name) var workingEnv: Int? = null,
    @SerializedName(WorkStyle_Field_Name) var workStyle: Int? = null,
    @SerializedName(WorkHours_Field_Name) var workingHours: Int? = null,
    @SerializedName(CurrentActivities_Field_Name) val curActivities: List<UserProperties>? = null,
    @SerializedName(PrefActivities_Field_Name) val prefActivities: List<UserProperties>? = null,
    @SerializedName(LifestyleTargets_Field_Name) val lifeStyleTargets: List<UserProperties>? = null,
    @SerializedName(WorkingTime_Field_Name) var workingTime: TimeSchedule = TimeSchedule(),
    @SerializedName(SleepTime_Field_Name) var sleepTime: TimeSchedule = TimeSchedule(), // missing in UI
) : Parcelable

@Parcelize
data class Diet(
    @SerializedName(Preference_Field_Name) var preference: List<UserProperties>? = null,
    @SerializedName(NonVeg_Field_Name) val nonVegDays: List<UserProperties>? = null,
    @SerializedName(Allergies_Field_Name) val allergies: List<UserProperties>? = null,
    @SerializedName(Cuisines_Field_Name) val cuisines: List<UserProperties>? = null,
    @SerializedName(Restrictions_Field_Name) val restrictions: List<UserProperties>? = null,
) : Parcelable

@Parcelize
data class TimeSchedule(
    @SerializedName(FromTime_Field_Name) val from: Float? = null,
    @SerializedName(ToTime_Field_Name) val to: Float? = null,
) : Parcelable
