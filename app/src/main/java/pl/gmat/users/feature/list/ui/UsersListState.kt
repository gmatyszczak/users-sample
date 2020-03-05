package pl.gmat.users.feature.list.ui

import pl.gmat.users.common.model.User

data class UsersListState(
    val users: List<User> = emptyList()
)