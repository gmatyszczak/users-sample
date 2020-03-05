package pl.gmat.users.feature.list.data

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.gmat.users.common.database.dao.AddressDao
import pl.gmat.users.common.database.dao.UserDao
import pl.gmat.users.common.mapper.UserMapper
import pl.gmat.users.testUser
import pl.gmat.users.testUserEntity

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UsersListRepositoryImplTest {

    @Mock
    private lateinit var mapperMock: UserMapper

    @Mock
    private lateinit var userDaoMock: UserDao

    @Mock
    private lateinit var addressDaoMock: AddressDao

    @InjectMocks
    private lateinit var repository: UsersListRepositoryImpl

    @Test
    fun `on load users`() = runBlockingTest {
        whenever(userDaoMock.loadAll()).thenReturn(flowOf(listOf(testUserEntity)))
        whenever(mapperMock.toUser(testUserEntity, emptyList())).thenReturn(testUser)

        repository.loadUsers().collect {
            assertEquals(listOf(testUser), it)
        }
    }

    @Test
    fun `on delete users`() = runBlockingTest {
        repository.deleteUsers()

        verify(userDaoMock).deleteAll()
        verify(addressDaoMock).deleteAll()
    }
}