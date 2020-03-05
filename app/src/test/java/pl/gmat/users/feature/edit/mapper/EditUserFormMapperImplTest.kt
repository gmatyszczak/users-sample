package pl.gmat.users.feature.edit.mapper

import org.junit.Assert.assertEquals
import org.junit.Test
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
        addresses = listOf(testAddress)
    )

    @Test
    fun `to edit user form`() {
        assertEquals(
            form,
            mapper.toEditUserForm(testUser)
        )
    }

    @Test
    fun `to user`() {
        assertEquals(
            testUser,
            mapper.toUser(
                form = form,
                id = 10
            )
        )
    }
}