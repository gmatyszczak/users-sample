package pl.gmat.users.feature.details

import pl.gmat.users.common.database.dao.AddressDao
import pl.gmat.users.common.database.dao.UserDao
import pl.gmat.users.common.mapper.UserMapper
import pl.gmat.users.common.model.User
import javax.inject.Inject

interface UserDetailsRepository {

    suspend fun loadUser(): User
    suspend fun deleteUser()
}

class UserDetailsRepositoryImpl @Inject constructor(
    private val userId: Long,
    private val mapper: UserMapper,
    private val userDao: UserDao,
    private val addressDao: AddressDao
) : UserDetailsRepository {

    override suspend fun loadUser(): User {
        val userEntity = userDao.load(userId)
        return mapper.toUser(userEntity, addressDao.load(userEntity.addressId))
    }

    override suspend fun deleteUser() = userDao.delete(userId)
}