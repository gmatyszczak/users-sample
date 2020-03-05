package pl.gmat.users.feature.details.ui

import pl.gmat.users.common.model.User

sealed class UserDetailsEffect {
    data class ShowEditUser(val user: User) : UserDetailsEffect()

    object Finish: UserDetailsEffect()
}