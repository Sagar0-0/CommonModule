package fit.asta.health.common.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.preference.PreferenceManager
import fit.asta.health.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefManager
@Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val resourcesProvider: ResourcesProvider
) {

    fun <T> getPreferences(keyId: Int, defaultValue: T): Flow<T> {
        val key = resourcesProvider.getString(keyId)
        return dataStore.data
            .catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map {
                val value = when (defaultValue) {
                    is String -> {
                        it[stringPreferencesKey(key)]
                    }

                    is Boolean -> {
                        it[booleanPreferencesKey(key)]
                    }

                    is Int -> {
                        it[intPreferencesKey(key)]
                    }

                    is Float -> {
                        it[floatPreferencesKey(key)]
                    }

                    is Long -> {
                        it[longPreferencesKey(key)]
                    }

                    is Double -> {
                        it[doublePreferencesKey(key)]
                    }

                    else -> {
                        it[stringPreferencesKey(key)]
                    }
                }
                Log.d("TAG", "getPreferences: $value --- $defaultValue")
                if (value == null) {
                    defaultValue
                } else {
                    value as T
                }
            }
    }

    suspend fun <T> setPreferences(keyId: Int, value: T) {
        try {
            val key = resourcesProvider.getString(keyId)
            dataStore.edit {
                when (value) {
                    is String -> {
                        it[stringPreferencesKey(key)] = value
                    }

                    is Boolean -> {
                        it[booleanPreferencesKey(key)] = value
                    }

                    is Int -> {
                        it[intPreferencesKey(key)] = value
                    }

                    is Float -> {
                        it[floatPreferencesKey(key)] = value
                    }

                    is Long -> {
                        it[longPreferencesKey(key)] = value
                    }

                    is Double -> {
                        it[doublePreferencesKey(key)] = value
                    }

                    else -> {}
                }
            }
        } catch (e: IOException) {
            Log.e("PREF", "setPreferences: $e")
        }

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
        fun getNotificationPermissionRejectedCount(context: Context): Int {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getInt("notification", 0)
        }

        fun setNotificationPermissionRejectedCount(context: Context, newCount: Int) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            preferences.edit().putInt("notification", newCount).apply()
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