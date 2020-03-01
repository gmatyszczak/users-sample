package pl.gmat.users.feature.details

import pl.gmat.users.common.model.User

data class UserDetailsState(
    val user: User = User()
)