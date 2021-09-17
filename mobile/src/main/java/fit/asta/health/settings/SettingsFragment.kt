package fit.asta.health.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import fit.asta.health.R
import fit.asta.health.utils.*


class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        setPreferencesFromResource(R.xml.setting_preferences, rootKey)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val prefTheme =
            findPreference<ListPreference>(resources.getString(R.string.user_pref_theme_key))
        prefTheme?.setOnPreferenceChangeListener { _, newValue ->

            setAppTheme(newValue.toString())
            true
        }

        val prefLang =
            findPreference<ListPreference>(resources.getString(R.string.user_pref_lang_key))
        prefLang?.setOnPreferenceChangeListener { preference, newValue ->

            view?.showSnackbar(preference.key + ":" + newValue)
            if (preference is ListPreference) {

                val index = preference.findIndexOfValue(newValue.toString())
                val entry = preference.entries[index]
                preference.title = "Language ($entry)"
            }

            true
        }

        showBuildVersion()

        // return inflater.inflate(R.layout.fragment_profile, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun showBuildVersion() {

        val prefVer =
            findPreference<Preference>(resources.getString(R.string.user_pref_version_key))
        prefVer?.summary = context?.getCurrentBuildVersion()
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        super.onPreferenceTreeClick(preference)

        when (preference.key) {
            resources.getString(R.string.user_pref_notification_key) -> {

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.settingsFragContainer,
                        NotifyFragment(), "notifyFrag"
                    )
                    //.addToBackStack(null)
                    .commit()
            }
            resources.getString(R.string.user_pref_purchase_key) -> {

                view?.showSnackbar("purchase")
            }
            resources.getString(R.string.user_pref_give_gift_key) -> {

                view?.showSnackbar("give_gift")
            }
            resources.getString(R.string.user_pref_donate_key) -> {

                view?.showSnackbar("donate")
            }
            resources.getString(R.string.user_pref_share_app_key) -> {

                context?.shareApp()
            }
            resources.getString(R.string.user_pref_rate_us_key) -> {

                context?.rateUs()
            }
            resources.getString(R.string.user_pref_feedback_key) -> {

                context?.sendFeedbackMessage()
            }
            resources.getString(R.string.user_pref_sign_out_key) -> {

                context?.signOut(requireView())
            }
            resources.getString(R.string.user_pref_delete_account_key) -> {

                context?.deleteAccount(requireView())
            }
            resources.getString(R.string.user_pref_bug_report_key) -> {

                context?.sendBugReportMessage()
            }
            resources.getString(R.string.user_pref_terms_of_use_key) -> {

                context?.showUrlInBrowser(
                    getPublicStorageUrl(
                        requireContext(), resources.getString(R.string.url_terms_of_use)
                    )
                )
            }
            resources.getString(R.string.user_pref_privacy_policy_key) -> {

                context?.showUrlInBrowser(
                    getPublicStorageUrl(
                        requireContext(),
                        resources.getString(R.string.url_privacy_policy)
                    )
                )
            }
        }

        return false
    }
}
