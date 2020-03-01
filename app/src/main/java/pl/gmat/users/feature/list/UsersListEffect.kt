package pl.gmat.users.feature.list

sealed class UsersListEffect {
    data class ShowUserDetails(val id: Long) : UsersListEffect()

    object ShowAddUser : UsersListEffect()
}
