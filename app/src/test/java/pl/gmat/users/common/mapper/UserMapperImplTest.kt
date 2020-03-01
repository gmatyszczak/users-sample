package pl.gmat.users.common.mapper

import org.junit.Assert.assertEquals
import org.junit.Test
import pl.gmat.users.testAddress
import pl.gmat.users.testAddressEntity
import pl.gmat.users.testUser
import pl.gmat.users.testUserEntity

class UserMapperImplTest {

    private val mapper = UserMapperImpl()

    @Test
    fun `to user`() {
        assertEquals(testUser, mapper.toUser(testUserEntity, testAddressEntity))
    }

    @Test
    fun `to address`() {
        assertEquals(testAddress, mapper.toAddress(testAddressEntity))
    }

    @Test
    fun `to user entity`() {
        assertEquals(testUserEntity, mapper.toUserEntity(testUser))
    }

    @Test
    fun `to address entity`() {
        assertEquals(testAddressEntity, mapper.toAddressEntity(testAddress))
    }
}