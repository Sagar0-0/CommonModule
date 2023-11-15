package fit.asta.health.feature.auth.util

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.firebase.ui.auth.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.resources.strings.R as StringR

@Composable
fun GoogleSignIn(
    modifier: Modifier = Modifier,
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
        modifier = modifier,
        onClick = {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(token)
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(context, gso)
            launcher.launch(googleSignInClient.signInIntent)
        }
    ) {

        // Google Icon Image
        AppLocalImage(
            modifier = Modifier
                .size(AppTheme.imageSize.level3)
                .padding(end = AppTheme.spacing.level0),
            painter = painterResource(id = R.drawable.googleg_standard_color_18),
            contentDescription = "google icon"
        )

        // Button Text
        CaptionTexts.Level1(
            text = stringResource(id = textId),
            color = AppTheme.colors.onSurface
        )
    }
}