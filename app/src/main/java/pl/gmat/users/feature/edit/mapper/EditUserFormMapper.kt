package pl.gmat.users.feature.edit.mapper

import pl.gmat.users.common.model.Address
import pl.gmat.users.common.model.Gender
import pl.gmat.users.common.model.User
import pl.gmat.users.feature.edit.model.EditUserForm
import javax.inject.Inject

interface EditUserFormMapper {

    fun toEditUserForm(user: User, addresses: List<Address>): EditUserForm
    fun toUser(
        form: EditUserForm,
        isAddNewAddress: Boolean,
        addresses: List<Address>,
        id: Long?
    ): User
}

class EditUserFormMapperImpl @Inject constructor() :
    EditUserFormMapper {

    override fun toEditUserForm(user: User, addresses: List<Address>) =
        EditUserForm(
            firstName = user.firstName,
            lastName = user.lastName,
            age = user.age,
            genderIndex = user.gender.ordinal,
            existingAddressIndex = addresses.indexOfFirst { it.id == user.address.id }
        )

    override fun toUser(
        form: EditUserForm,
        isAddNewAddress: Boolean,
        addresses: List<Address>,
        id: Long?
    ): User {
        val address =
            if (isAddNewAddress) {
                Address(value = form.newAddress)
            } else {
                addresses[form.existingAddressIndex]
            }
        return User(
            id = id ?: 0,
            firstName = form.firstName,
            lastName = form.lastName,
            age = form.age,
            gender = Gender.values()[form.genderIndex],
            address = address
        )
    }
}