package fit.asta.health.settings

import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.messaging.FirebaseMessaging
import fit.asta.health.R
import fit.asta.health.common.utils.PrefUtils

class NotifyFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        setPreferencesFromResource(R.xml.notification_preferences, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        super.onPreferenceTreeClick(preference)

        when (preference.key) {
            resources.getString(R.string.user_pref_master_notification_key) -> {

                (activity as SettingsActivity).notifyChange()
            }
            resources.getString(R.string.user_pref_new_release_key) -> {

                if (PrefUtils.getNewReleaseNotification(requireContext()))
                    subscribeTopic(preference.key)
                else
                    unSubscribeTopic(preference.key)
            }
            resources.getString(R.string.user_pref_health_tips_key) -> {

                if (PrefUtils.getHealthTipsNotification(requireContext()))
                    subscribeTopic(preference.key)
                else
                    unSubscribeTopic(preference.key)
            }
            resources.getString(R.string.user_pref_promotions_key) -> {

                if (PrefUtils.getPromotionsNotification(requireContext()))
                    subscribeTopic(preference.key)
                else
                    unSubscribeTopic(preference.key)
            }
        }

        return false
    }

    private fun subscribeTopic(topic: String) {

        FirebaseMessaging.getInstance().subscribeToTopic(topic)
            .addOnFailureListener {

                /*val msg = getString(R.string.message_subscribe_failed)
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()*/
                Log.e("subscribeTopic: ", topic + " - " + it.message)
            }
    }

    private fun unSubscribeTopic(topic: String) {

        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
            .addOnFailureListener {

                /*val msg = getString(R.string.message_subscribe_failed)
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()*/
                Log.e("unSubscribeTopic: ", topic + " - " + it.message)
            }
    }
}
