package fit.asta.health.feature.profile.ui

import com.google.firebase.auth.AuthCredential
import fit.asta.health.data.profile.remote.model.BasicProfileDTO

sealed interface BasicProfileEvent {
    data class Link(val cred: AuthCredential) : BasicProfileEvent
    data class CheckReferralCode(val code: String) : BasicProfileEvent
    data class CreateBasicProfile(val basicProfileDTO: BasicProfileDTO) : BasicProfileEvent
    data object ResetCodeState : BasicProfileEvent
    data object NavigateToHome : BasicProfileEvent
    data object ResetCreateProfileState : BasicProfileEvent
}