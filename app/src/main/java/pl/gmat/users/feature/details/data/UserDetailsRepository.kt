package pl.gmat.users.feature.details.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import pl.gmat.users.common.database.dao.AddressDao
import pl.gmat.users.common.database.dao.UserDao
import pl.gmat.users.common.mapper.UserMapper
import pl.gmat.users.common.model.User
import javax.inject.Inject

interface UserDetailsRepository {

    fun loadUser(): Flow<User?>
    suspend fun deleteUser()
}

@ExperimentalCoroutinesApi
class UserDetailsRepositoryImpl @Inject constructor(
    private val userId: Long,
    private val mapper: UserMapper,
    private val userDao: UserDao,
    private val addressDao: AddressDao
) : UserDetailsRepository {

    override fun loadUser() =
        userDao.load(userId).combine(addressDao.loadForUserId(userId)) { user, addresses ->
            user?.let { mapper.toUser(it, addresses) }
        }

    override suspend fun deleteUser() {
        userDao.delete(userId)
        addressDao.deleteForUserId(userId)
    }
}