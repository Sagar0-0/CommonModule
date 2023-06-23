package fit.asta.health.auth

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fit.asta.health.R
import fit.asta.health.common.ui.theme.buttonSize
import fit.asta.health.common.ui.theme.spacing

@Composable
fun SignInScreen(navHostController: NavHostController, onSuccess: () -> Unit) {
    val auth = Firebase.auth
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        Icon(
            painter = painterResource(id = R.mipmap.ic_launcher_foreground),
            contentDescription = "",
            modifier = Modifier.weight(1f)
        )

        OutlinedButton(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonSize.extraLarge),
            onClick = {
                navHostController.navigate("Phone")
            },
            content = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Icon(
                            tint = MaterialTheme.colorScheme.primary,
                            painter = painterResource(id = com.firebase.ui.auth.R.drawable.fui_ic_phone_white_24dp),
                            contentDescription = null,
                        )
                        Text(
                            style = MaterialTheme.typography.labelMedium,
                            text = "Sign in with Phone"
                        )
                        Icon(
                            tint = Color.Transparent,
                            imageVector = Icons.Default.MailOutline,
                            contentDescription = null,
                        )
                    }
                )
            }
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            onSuccess()
                        }
                    }
                    .addOnFailureListener {
                        Log.e("Login", "GoogleSignIn", it)
                    }

            } catch (e: ApiException) {
                Log.e("TAG", "Google sign in failed", e)
            }
        }

        val token = stringResource(R.string.default_web_client_id)

        OutlinedButton(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonSize.extraLarge),
            onClick = {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token)
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)
            },
            content = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Icon(
                            tint = Color.Unspecified,
                            painter = painterResource(id = com.firebase.ui.auth.R.drawable.googleg_standard_color_18),
                            contentDescription = null,
                        )
                        Text(
                            style = MaterialTheme.typography.labelMedium,
                            text = "Sign in with Google"
                        )
                        Icon(
                            tint = Color.Transparent,
                            imageVector = Icons.Default.MailOutline,
                            contentDescription = null,
                        )
                    }
                )
            }
        )

        val annotatedLinkString: AnnotatedString = buildAnnotatedString {

            val str =
                "By continuing, you are indicating that you accept our Terms of Service and Privacy Policy."
            val startTIndex = str.indexOf("Terms")
            val endTIndex = startTIndex + 16
            val startPIndex = str.indexOf("Privacy")
            val endPIndex = startPIndex + 14
            append(str)
            addStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                ), start = startTIndex, end = endTIndex
            )
            addStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                ), start = startPIndex, end = endPIndex
            )

            addStringAnnotation(
                tag = "terms",
                annotation = "https://firebasestorage.googleapis.com/v0/b/asta-health-dev-963f0.appspot.com/o/tos_pp%2Fterms_of_use.htm?alt=media",
                start = startTIndex,
                end = endTIndex
            )

            addStringAnnotation(
                tag = "privacy",
                annotation = "https://firebasestorage.googleapis.com/v0/b/asta-health-dev-963f0.appspot.com/o/tos_pp%2Fprivacy_policy.htm?alt=media",
                start = startPIndex,
                end = endPIndex
            )

        }

        val uriHandler = LocalUriHandler.current

        ClickableText(
            modifier = Modifier
                .padding(vertical = spacing.medium)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            text = annotatedLinkString,
            onClick = {
                annotatedLinkString
                    .getStringAnnotations("terms", it, it)
                    .firstOrNull()?.let { stringAnnotation ->
                        uriHandler.openUri(stringAnnotation.item)
                    }
                annotatedLinkString
                    .getStringAnnotations("privacy", it, it)
                    .firstOrNull()?.let { stringAnnotation ->
                        uriHandler.openUri(stringAnnotation.item)
                    }
            }
        )
    }
}
