package fit.asta.health.firebase.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.*
import fit.asta.health.R
import fit.asta.health.firebase.viewmodel.AuthViewModel
import fit.asta.health.common.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class AuthActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.auth_activity)

        if (viewModel.isAuthenticated()) {

            startMainActivity()
        } else {

            applicationContext.setAppTheme()
            startFireBaseActivity()
        }
    }

    private fun startFireBaseActivity() {

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                    arrayOf(
                        AuthUI.IdpConfig.PhoneBuilder().build(),
                        AuthUI.IdpConfig.GoogleBuilder().build()
                    ).asList()
                )
                .setLogo(R.mipmap.ic_launcher_foreground)
                .setTheme(R.style.LoginTheme)
                .setTosAndPrivacyPolicyUrls(
                    getPublicStorageUrl(
                        this,
                        resources.getString(R.string.url_terms_of_use)
                    ),
                    getPublicStorageUrl(
                        this,
                        resources.getString(R.string.url_privacy_policy)
                    )
                )
                .build(), RC_SIGN_IN
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_CANCELED) {

            finishAndRemoveTask() // User pressed back button, exit the application
        }

        if (requestCode == RC_SIGN_IN) {

            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {

                startMainActivity()

            } else {

                if (response?.error?.message != null) {

                    this.findViewById<RelativeLayout>(R.id.authLayout)
                        .showSnackbar(response.error?.message!!)
                    Log.d("Error: ", response.error?.toString()!!)
                }
            }
        }
    }

    private fun getSelectedProviders(): List<AuthUI.IdpConfig> {

        return arrayOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        ).asList()
    }

    fun linkWithPhoneNumber() {

        val credential = GoogleAuthProvider.getCredential("", null)
        FirebaseAuth.getInstance().currentUser?.linkWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // Log.d(TAG, "linkWithCredential:success")
                    val user = task.result?.user
                    updateUI(user)
                } else {
                    // Log.w(TAG, "linkWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

                // ...
            }
    }

    private fun linkAndMerge(credential: AuthCredential) {

        val mAuth = FirebaseAuth.getInstance()
        //val prevUser = FirebaseAuth.getInstance().currentUser
        mAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                //val currentUser = it.user
                // Merge prevUser and currentUser accounts and data
                // ...
            }
            .addOnFailureListener {
                // ...
            }
    }

    private fun unlink(providerId: String) {

        val mAuth = FirebaseAuth.getInstance()
        mAuth.currentUser!!.unlink(providerId)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    // Auth provider unlinked from account
                    // ...
                }
            }
    }

    private fun reauthenticate() {

        val user = FirebaseAuth.getInstance().currentUser

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        val credential = EmailAuthProvider
            .getCredential("user@intuminds.com", "password1234")

        // Prompt the user to re-provide their sign-in credentials
        user?.reauthenticate(credential)
            ?.addOnCompleteListener {
                // Log.d(TAG, "User re-authenticated.")
            }
    }

    private fun buildActionCodeSettings() {

        /*val actionCodeSettings = ActionCodeSettings.newBuilder()
            // URL you want to redirect back to. The domain (www.intuminds.com) for this
            // URL must be whitelisted in the Firebase Console.
            .setUrl("https://www.asta.fit/finishSignUp?cartId=1234")
            // This must be true
            .setHandleCodeInApp(true)
            .setIOSBundleId("fit.asta.ios")
            .setAndroidPackageName(
                "fit.asta.android",
                true,  // installIfNotAvailable
                "12"  // minimumVersion
            )
            .build()*/
    }

    private fun getGoogleCredentials() {

        val googleIdToken = ""
        GoogleAuthProvider.getCredential(googleIdToken, null)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun updateUI(user: FirebaseUser?) {
        // No-op
    }
}