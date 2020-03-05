package pl.gmat.users.feature.edit.data

import com.nhaarman.mockitokotlin2.inOrder
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
    fun `on insert or update user`() = runBlockingTest {
        whenever(mapperMock.toUserEntity(testUser)).thenReturn(testUserEntity)
        whenever(userDaoMock.insertOrUpdate(testUserEntity)).thenReturn(testUserEntity.id)
        whenever(mapperMock.toAddressEntity(testAddress, testUser.id)).thenReturn(testAddressEntity)

        repository.insertOrUpdateUser(testUser)

        inOrder(addressDaoMock) {
            verify(addressDaoMock).deleteForUserId(testUserEntity.id)
            verify(addressDaoMock).insert(testAddressEntity)
            verifyNoMoreInteractions()
        }
    }
}