package pl.gmat.users.feature.edit.model

import pl.gmat.users.common.model.Address
import pl.gmat.users.common.model.Gender

data class EditUserForm(
    val firstName: String = "",
    val lastName: String = "",
    val age: String = "",
    val genderIndex: Int = Gender.MALE.ordinal,
    val addresses: List<Address> = emptyList()
)