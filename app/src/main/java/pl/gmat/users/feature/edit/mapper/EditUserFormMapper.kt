package pl.gmat.users.feature.edit.mapper

import pl.gmat.users.common.model.Gender
import pl.gmat.users.common.model.User
import pl.gmat.users.feature.edit.model.EditUserForm
import javax.inject.Inject

interface EditUserFormMapper {

    fun toEditUserForm(user: User): EditUserForm
    fun toUser(
        form: EditUserForm,
        id: Long?
    ): User
}

class EditUserFormMapperImpl @Inject constructor() :
    EditUserFormMapper {

    override fun toEditUserForm(user: User) =
        EditUserForm(
            firstName = user.firstName,
            lastName = user.lastName,
            age = user.age,
            genderIndex = user.gender.ordinal,
            addresses = user.addresses
        )

    override fun toUser(
        form: EditUserForm,
        id: Long?
    ) = User(
        id = id ?: 0,
        firstName = form.firstName,
        lastName = form.lastName,
        age = form.age,
        gender = Gender.values()[form.genderIndex],
        addresses = form.addresses
    )
}