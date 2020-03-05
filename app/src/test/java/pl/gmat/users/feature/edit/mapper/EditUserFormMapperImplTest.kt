package pl.gmat.users.feature.edit.mapper

import org.junit.Assert.assertEquals
import org.junit.Test
import pl.gmat.users.common.model.Address
import pl.gmat.users.feature.edit.model.EditUserForm
import pl.gmat.users.testAddress
import pl.gmat.users.testUser

class EditUserFormMapperImplTest {

    private val mapper =
        EditUserFormMapperImpl()

    private val form = EditUserForm(
        firstName = testUser.firstName,
        lastName = testUser.lastName,
        genderIndex = testUser.gender.ordinal,
        age = testUser.age,
        existingAddressIndex = 0,
        newAddress = "test"
    )

    @Test
    fun `to edit user form`() {
        assertEquals(
            form.copy(newAddress = ""),
            mapper.toEditUserForm(testUser, listOf(testAddress))
        )
    }

    @Test
    fun `when is add new address true on to user`() {
        assertEquals(
            testUser.copy(address = Address(value = "test")),
            mapper.toUser(
                form = form,
                isAddNewAddress = true,
                addresses = emptyList(),
                id = 10
            )
        )
    }

    @Test
    fun `when is add new address false on to user`() {
        assertEquals(
            testUser,
            mapper.toUser(
                form = form,
                isAddNewAddress = false,
                addresses = listOf(testAddress),
                id = 10
            )
        )
    }
}