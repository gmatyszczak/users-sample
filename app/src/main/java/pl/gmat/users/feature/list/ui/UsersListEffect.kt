package pl.gmat.users.feature.list.ui

sealed class UsersListEffect {
    data class ShowUserDetails(val id: Long) : UsersListEffect()

    object ShowAddUser : UsersListEffect()
}
