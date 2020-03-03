package pl.gmat.users.feature.edit

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.gmat.users.common.database.dao.AddressDao
import pl.gmat.users.common.database.dao.UserDao
import pl.gmat.users.common.mapper.UserMapper
import pl.gmat.users.testAddress
import pl.gmat.users.testAddressEntity
import pl.gmat.users.testUser
import pl.gmat.users.testUserEntity

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EditUserRepositoryImplTest {

    @Mock
    private lateinit var mapperMock: UserMapper

    @Mock
    private lateinit var userDaoMock: UserDao

    @Mock
    private lateinit var addressDaoMock: AddressDao

    @InjectMocks
    private lateinit var repository: EditUserRepositoryImpl

    @Test
    fun `when is new address on add user`() = runBlockingTest {
        whenever(mapperMock.toAddressEntity(testAddress)).thenReturn(testAddressEntity)
        val newId = 100L
        whenever(addressDaoMock.insert(testAddressEntity)).thenReturn(newId)
        val userEntityToInsert = testUserEntity.copy(addressId = newId)
        whenever(mapperMock.toUserEntity(testUser.copy(address = testUser.address.copy(id = newId))))
            .thenReturn(userEntityToInsert)

        repository.insertOrUpdateUser(testUser, isNewAddress = true)

        verify(userDaoMock).insertOrUpdate(userEntityToInsert)
    }

    @Test
    fun `when is not new address on add user`() = runBlockingTest {
        whenever(mapperMock.toUserEntity(testUser)).thenReturn(testUserEntity)

        repository.insertOrUpdateUser(testUser, isNewAddress = false)

        verify(userDaoMock).insertOrUpdate(testUserEntity)
    }
}