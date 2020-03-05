package pl.gmat.users.feature.details

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.gmat.users.common.database.dao.AddressDao
import pl.gmat.users.common.database.dao.UserDao
import pl.gmat.users.common.mapper.UserMapper
import pl.gmat.users.common.model.User
import javax.inject.Inject

interface UserDetailsRepository {

    fun loadUser(): Flow<User?>
    suspend fun deleteUser()
}

class UserDetailsRepositoryImpl @Inject constructor(
    private val userId: Long,
    private val mapper: UserMapper,
    private val userDao: UserDao,
    private val addressDao: AddressDao
) : UserDetailsRepository {

    override fun loadUser() = userDao.load(userId).map {
        it?.let { mapper.toUser(it, addressDao.load(it.addressId)) }
    }

    override suspend fun deleteUser() = userDao.delete(userId)
}