package fit.asta.health.feature.auth.util

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.resources.strings.R as StringR

@Composable
fun GoogleSignIn(
    @StringRes textId: Int,
    signInWithCredentials: (AuthCredential) -> Unit
) {
    val context = LocalContext.current
    val token = stringResource(StringR.string.default_web_client_id)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            signInWithCredentials(credential)
        } catch (e: ApiException) {
            Log.e("Login", "Google sign in failed", e)
        }
    }

    AppOutlinedButton(
        textToShow = stringResource(id = textId),
        modifier = Modifier
            .fillMaxWidth()
            .height(AppTheme.buttonSize.level6),
        leadingIcon = painterResource(id = com.firebase.ui.auth.R.drawable.googleg_standard_color_18)
    ) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(token)
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        launcher.launch(googleSignInClient.signInIntent)
    }
}