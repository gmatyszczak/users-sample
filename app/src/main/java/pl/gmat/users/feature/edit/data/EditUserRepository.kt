package pl.gmat.users.feature.edit.data

import pl.gmat.users.common.database.dao.AddressDao
import pl.gmat.users.common.database.dao.UserDao
import pl.gmat.users.common.mapper.UserMapper
import pl.gmat.users.common.model.User
import javax.inject.Inject

interface EditUserRepository {

    suspend fun insertOrUpdateUser(user: User)
}

class EditUserRepositoryImpl @Inject constructor(
    private val mapper: UserMapper,
    private val userDao: UserDao,
    private val addressDao: AddressDao
) : EditUserRepository {

    override suspend fun insertOrUpdateUser(user: User) {
        val id = addressDao.insert(mapper.toAddressEntity(user.address))
        val userToInsert = user.copy(address = user.address.copy(id = id))
        userDao.insertOrUpdate(mapper.toUserEntity(userToInsert))
    }
}