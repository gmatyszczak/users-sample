package pl.gmat.users.feature.edit

import pl.gmat.users.common.model.Address

data class EditUserState(
    val isAddNewAddressChecked: Boolean = true,
    val addresses: List<Address> = emptyList(),
    val submitButtonTextResId: Int = 0
)