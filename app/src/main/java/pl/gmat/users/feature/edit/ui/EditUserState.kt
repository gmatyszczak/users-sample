package pl.gmat.users.feature.edit.ui

import pl.gmat.users.common.model.Address

data class EditUserState(
    val submitButtonTextResId: Int = 0,
    val addresses: List<Address> = emptyList()
)