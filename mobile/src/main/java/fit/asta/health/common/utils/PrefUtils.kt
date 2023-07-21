package fit.asta.health.common.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import fit.asta.health.R
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefUtils
@Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val resourcesProvider: ResourcesProvider
) {

    fun getPromotionsNotification(): Boolean {
        return sharedPreferences.getBoolean(
            resourcesProvider.getString(R.string.user_pref_promotions_key),
            true
        )
    }

    fun getHealthTipsNotification(): Boolean {
        return sharedPreferences.getBoolean(
            resourcesProvider.getString(R.string.user_pref_health_tips_key),
            true
        )
    }

    fun getMasterNotification(): Boolean {

        return sharedPreferences.getBoolean(
            resourcesProvider.getString(R.string.user_pref_master_notification_key),
            true
        )
    }

    fun setMasterNotification(status: Boolean) {

        sharedPreferences.edit()
            .putBoolean(
                resourcesProvider.getString(R.string.user_pref_master_notification_key),
                status
            )
            .apply()
    }

    fun getCurrentAddress(): String {
        return sharedPreferences.getString(
            resourcesProvider.getString(R.string.user_pref_current_address),
            null
        ) ?: "Select location"
    }

    fun setCurrentAddress(address: String) {
        sharedPreferences.edit()
            .putString(resourcesProvider.getString(R.string.user_pref_current_address), address)
            .apply()
    }

    fun getNewReleaseNotification(): Boolean {

        return sharedPreferences.getBoolean(
            resourcesProvider.getString(R.string.user_pref_new_release_key),
            true
        )
    }

    companion object {

        fun getLocationPermissionRejectedCount(context: Context): Int {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getInt(
                context.getString(R.string.user_pref_location_permission_count),
                0
            )
        }

        fun setLocationPermissionRejectedCount(context: Context, newCount: Int) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            preferences.edit()
                .putInt(context.getString(R.string.user_pref_location_permission_count), newCount)
                .apply()
        }

        fun getOnboardingShownStatus(context: Context): Boolean {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getBoolean(
                context.getString(R.string.user_pref_onboarding_shown),
                false
            )
        }

        fun setOnboardingShownStatus(status: Boolean, context: Context) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            preferences.edit()
                .putBoolean(context.getString(R.string.user_pref_onboarding_shown), status)
                .apply()
        }

        fun getUnitHeight(context: Context): String {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(
                context.getString(R.string.user_pref_unit_height_key),
                "centimetres"
            )!!
        }

        fun setUnitHeight(unit: String, context: Context) {

            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(context.getString(R.string.user_pref_unit_height_key), unit)
            editor.apply()
        }

        fun getUnitWeight(context: Context): String {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(
                context.getString(R.string.user_pref_unit_weight_key),
                "kilograms"
            )!!
        }

        fun setUnitWeight(unit: String, context: Context) {

            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(context.getString(R.string.user_pref_unit_weight_key), unit)
            editor.apply()
        }

        fun getUnitEnergy(context: Context): String {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(
                context.getString(R.string.user_pref_unit_energy_key),
                "calories"
            )!!
        }

        fun setUnitEnergy(unit: String, context: Context) {

            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(context.getString(R.string.user_pref_unit_energy_key), unit)
            editor.apply()
        }

        fun getUnitDistance(context: Context): String {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(
                context.getString(R.string.user_pref_unit_distance_key),
                "kilometres"
            )!!
        }

        fun setUnitDistance(unit: String, context: Context) {

            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(context.getString(R.string.user_pref_unit_distance_key), unit)
            editor.apply()
        }

        fun getTheme(context: Context): String {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(
                context.getString(R.string.user_pref_theme_key),
                AppThemeType.System.value
            )!!
        }

        fun setTheme(type: String, context: Context) {

            val themeType = type.ifEmpty { AppThemeType.System.value }
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(context.getString(R.string.user_pref_theme_key), themeType)
            editor.apply()
        }

        fun getLanguage(context: Context): String {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(
                context.getString(R.string.user_pref_unit_height_key),
                "en"
            )!!
        }

        fun setLanguage(code: String, context: Context) {

            val langCode = if (code.isNotEmpty()) code else "en"
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(context.getString(R.string.user_pref_unit_height_key), langCode)
            editor.apply()
        }

        private fun getBreathDate(context: Context): Long {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong("breath_date", System.currentTimeMillis())
        }

        fun setBreathDate(millis: Long, context: Context) {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            preferences.edit().putLong("breath_date", millis).apply()
        }

        fun getBreathDuration(context: Context): Int {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getInt("user_pref_breath_duration", 5)
        }

        fun setBreathDuration(mins: Int, context: Context) {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            preferences.edit().putInt("user_pref_breath_duration", mins).apply()
        }

        fun getAsanaDay(context: Context): Int {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getInt("user_pref_asana_day_count", 1)
        }

        fun setAsanaDay(count: Int, context: Context) {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            preferences.edit().putInt("user_pref_asana_day_count", count).apply()
        }

        fun getAsanaLevel(context: Context): String {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString("user_pref_asana_level", "L1")!!
        }

        fun setAsanaLevel(code: String, context: Context) {

            val levelCode = if (code.isNotEmpty()) code else "L1"
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString("user_pref_asana_level", levelCode)
            editor.apply()
        }

        fun getAsanaHeal(context: Context): String {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString("user_pref_asana_heal", "general")!!
        }

        fun setAsanaHeal(code: String, context: Context) {

            val levelCode = if (code.isNotEmpty()) code else "general"
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString("user_pref_asana_heal", levelCode)
            editor.apply()
        }

        fun getAsanaDuration(context: Context): Int {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getInt("user_pref_asana_duration", 40)
        }

        fun setAsanaDuration(mins: Int, context: Context) {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            preferences.edit().putInt("user_pref_asana_duration", mins).apply()
        }

        fun getAsanaStyle(context: Context): Set<String> {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getStringSet("user_pref_asana_style", setOf("S0"))!!
        }

        fun setAsanaStyle(set: Set<String>?, context: Context) {

            val values = if (set !== null && set.isNotEmpty()) set else setOf("S0")
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putStringSet("user_pref_asana_style", values)
            editor.apply()
        }

        fun setAsanaStretch(set: Set<String>?, context: Context) {

            val values = if (set !== null && set.isNotEmpty()) set else setOf("P0")
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putStringSet("user_pref_asana_stretch", values)
            editor.apply()
        }

        fun getAsanaStretch(context: Context): Set<String> {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getStringSet("user_pref_asana_stretch", setOf("P0"))!!
        }

        fun setAsanaStrength(set: Set<String>?, context: Context) {

            val values = if (set !== null && set.isNotEmpty()) set else setOf("P0")
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putStringSet("user_pref_asana_strength", values)
            editor.apply()
        }

        fun getAsanaStrength(context: Context): Set<String> {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getStringSet("user_pref_asana_strength", setOf("P0"))!!
        }

        fun setAsanaFocus(set: Set<String>?, context: Context) {

            val values = if (set !== null && set.isNotEmpty()) set else setOf("F0")
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putStringSet("user_pref_asana_focus", values)
            editor.apply()
        }

        fun getAsanaFocus(context: Context): Set<String> {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getStringSet("user_pref_asana_focus", setOf("F0"))!!
        }

        fun getFormattedBreathDate(context: Context): String {

            val cal = Calendar.getInstance()
            cal.timeInMillis =
                getBreathDate(context)

            return String.format(
                Locale.US,
                "Last at %tl:%tM %tp on %tb %td %tY",
                cal, cal, cal, cal, cal, cal
            )
        }
    }
}
