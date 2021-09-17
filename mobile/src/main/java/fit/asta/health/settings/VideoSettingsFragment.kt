package fit.asta.health.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import fit.asta.health.R


class VideoSettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        setPreferencesFromResource(R.xml.video_preferences, rootKey)
    }
}
