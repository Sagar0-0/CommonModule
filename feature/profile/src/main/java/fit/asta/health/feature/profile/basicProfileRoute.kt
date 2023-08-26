package fit.asta.health.feature.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TextField
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import fit.asta.health.auth.model.domain.User
import fit.asta.health.auth.model.domain.toUser
import fit.asta.health.common.utils.popUpToTop

internal const val BASIC_PROFILE_GRAPH_ROUTE = "graph_basic_profile"

fun NavController.navigateToBasicProfile(user: User, navOptions: NavOptions? = null) {
    val json = user.toString()
    if (navOptions != null) {
        this.navigate("$BASIC_PROFILE_GRAPH_ROUTE/$json", navOptions)
    } else {
        this.navigate("$BASIC_PROFILE_GRAPH_ROUTE/$json") {
            popUpToTop(this@navigateToBasicProfile)
        }
    }
}

fun NavGraphBuilder.basicProfileRoute(navigateToHome: () -> Unit) {

    composable("$BASIC_PROFILE_GRAPH_ROUTE/{user}") {
        val user: User? = it.arguments?.getString("user")?.toUser()
        user?.let {
            Column {
                TextField(value = user.uid, onValueChange = {})
                TextField(value = user.email ?: "", onValueChange = {})
                TextField(value = user.name ?: "", onValueChange = {})
                TextField(value = user.phoneNumber ?: "", onValueChange = {})
                TextField(value = user.photoUrl.toString(), onValueChange = {})
            }
        }
    }
}