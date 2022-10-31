package fit.asta.health.settings

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.appbar.MaterialToolbar
import fit.asta.health.R


private const val TITLE_TAG = "settingsActivityTitle"

class SettingsActivity : AppCompatActivity(),
    PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    companion object {

        const val NOTIFY_CHANGE = 578
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        val toolbar: Toolbar = findViewById(R.id.settingsToolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        if (savedInstanceState == null) {

            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.settingsFragContainer,
                    SettingsFragment()
                )
                .commit()
        } else {

            toolbar.title = savedInstanceState.getCharSequence(TITLE_TAG)
        }

        supportFragmentManager.addOnBackStackChangedListener {

            if (supportFragmentManager.backStackEntryCount == 0) {

                toolbar.setTitle(R.string.title_settings)
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save current activity title so we can set it again after a configuration change
        outState.putCharSequence(
            TITLE_TAG,
            findViewById<MaterialToolbar>(R.id.settingsToolbar).title
        )
    }

    override fun onSupportNavigateUp(): Boolean {

        if (supportFragmentManager.popBackStackImmediate()) {

            return true
        }

        return super.onSupportNavigateUp()
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat,
        pref: Preference
    ): Boolean {

        val args = pref.extras
        val fragment = supportFragmentManager.fragmentFactory
            .instantiate(classLoader, pref.fragment!!).apply {

                arguments = args
                setTargetFragment(caller, 0)
            }

        // Replace the existing Fragment with the new Fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.settingsFragContainer, fragment)
            .addToBackStack(null)
            .commit()

        findViewById<MaterialToolbar>(R.id.settingsToolbar).title = pref.title
        return true
    }

    fun notifyChange() {

        setResult(Activity.RESULT_OK, null)
    }
}
