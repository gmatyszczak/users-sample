package pl.gmat.users.feature.details.data

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.gmat.users.common.database.dao.AddressDao
import pl.gmat.users.common.database.dao.UserDao
import pl.gmat.users.common.mapper.UserMapper
import pl.gmat.users.testAddressEntity
import pl.gmat.users.testUser
import pl.gmat.users.testUserEntity

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserDetailsRepositoryImplTest {

    @Mock
    private lateinit var mapperMock: UserMapper

    @Mock
    private lateinit var userDaoMock: UserDao

    @Mock
    private lateinit var addressDaoMock: AddressDao

    private lateinit var repository: UserDetailsRepositoryImpl

    @Before
    fun setup() {
        repository =
            UserDetailsRepositoryImpl(
                testUser.id,
                mapperMock,
                userDaoMock,
                addressDaoMock
            )
    }

    @Test
    fun `on load user`() = runBlockingTest {
        whenever(userDaoMock.load(testUser.id)).thenReturn(flowOf(testUserEntity))
        whenever(addressDaoMock.loadForUserId(testUser.id)).thenReturn(listOf(testAddressEntity))
        whenever(mapperMock.toUser(testUserEntity, listOf(testAddressEntity))).thenReturn(testUser)

        repository.loadUser().collect {
            assertEquals(testUser, it)
        }
    }

    @Test
    fun `on delete user`() = runBlockingTest {
        repository.deleteUser()

        verify(userDaoMock).delete(testUser.id)
    }

}