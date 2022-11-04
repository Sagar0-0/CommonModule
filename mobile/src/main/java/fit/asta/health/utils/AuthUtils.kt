package fit.asta.health.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthProvider
import fit.asta.health.MainActivity
import fit.asta.health.firebase.view.AuthActivity


const val RC_SIGN_IN: Int = 6789 // Any number you want
const val APP_USER: String = "APP_USER"


fun Context.startMainActivity() =
    Intent(this, MainActivity::class.java).also {

        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)

        val activity = this as Activity
        activity.finishAffinity()
    }

fun Context.startAuthActivity() =
    Intent(this, AuthActivity::class.java).also {

        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)

        val activity = this as Activity
        activity.finishAffinity()
    }

fun Context.signOut(view: View) =
    AuthUI.getInstance().signOut(this)
        .addOnCompleteListener { signOutTask ->

            if (signOutTask.isSuccessful) {

                startMainActivity()
            } else {

                view.showSnackbar(signOutTask.exception?.message!!)
            }
        }

fun Context.deleteAccount(view: View) {

    val currentUser = FirebaseAuth.getInstance().currentUser ?: return
    val credential: AuthCredential = when (currentUser.providerData[1].providerId) {
        "google.com" -> {

            val fireBaseContext = FirebaseAuth.getInstance().app.applicationContext
            val googleAccount = GoogleSignIn.getLastSignedInAccount(fireBaseContext)
            GoogleAuthProvider.getCredential(googleAccount?.idToken, null)

        }
        "phone" -> {

            // How to get the below params(verificationId, code), when we use firebase auth ui?
            PhoneAuthProvider.getCredential(currentUser.phoneNumber!!, "")
        }
        else -> return
    }

    deleteAccount(credential, view)
}

fun Context.deleteAccount(credential: AuthCredential, view: View) =
    reAuthenticateUser(credential)
        ?.addOnCompleteListener { reAuthTask ->

            if (reAuthTask.isSuccessful) {

                AuthUI.getInstance().delete(this)
                    .addOnCompleteListener { deleteTask ->

                        if (deleteTask.isSuccessful) {

                            startMainActivity()
                        } else {

                            view.showSnackbar(deleteTask.exception?.message!!)
                            Log.d("DeleteAccount", deleteTask.exception?.message!!)
                        }
                    }
            } else { //Handle the exception

                view.showSnackbar(reAuthTask.exception?.message!!)
                Log.d("ReAuth", reAuthTask.exception?.message!!)
            }
        }

fun reAuthenticateUser(credential: AuthCredential) =
    FirebaseAuth.getInstance().currentUser?.reauthenticate(credential)

fun Context.silentSignIn(view: View) =
    AuthUI.getInstance()
        .silentSignIn(
            this, arrayOf(
                AuthUI.IdpConfig.PhoneBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build()
            ).asList()
        ).addOnCompleteListener { signInTask ->

            if (signInTask.isSuccessful) {

                startMainActivity()
            } else {

                view.showSnackbar(signInTask.exception?.message!!)
                Log.d("silentSignIn", signInTask.exception?.message!!)
            }
        }
        .addOnFailureListener { e ->

            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }