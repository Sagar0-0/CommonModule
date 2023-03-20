package fit.asta.health.profile.model.domain

sealed class UserType {
    object Male : UserType()
    object Female : UserType()
    object Other : UserType()
}

sealed class UserSelection {
    object Yes : UserSelection()
    object No : UserSelection()
}