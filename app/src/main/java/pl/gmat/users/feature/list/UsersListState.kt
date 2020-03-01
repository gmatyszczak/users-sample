package pl.gmat.users.feature.list

import pl.gmat.users.common.model.User

data class UsersListState(
    val users: List<User> = emptyList()
)