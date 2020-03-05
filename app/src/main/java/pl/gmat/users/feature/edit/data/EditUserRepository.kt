package pl.gmat.users.feature.edit.data

import pl.gmat.users.common.database.dao.AddressDao
import pl.gmat.users.common.database.dao.UserDao
import pl.gmat.users.common.mapper.UserMapper
import pl.gmat.users.common.model.Address
import pl.gmat.users.common.model.User
import javax.inject.Inject

interface EditUserRepository {

    suspend fun insertOrUpdateUser(user: User, isNewAddress: Boolean = false)
    suspend fun loadAddresses(): List<Address>
}

class EditUserRepositoryImpl @Inject constructor(
    private val mapper: UserMapper,
    private val userDao: UserDao,
    private val addressDao: AddressDao
) : EditUserRepository {

    override suspend fun insertOrUpdateUser(user: User, isNewAddress: Boolean) {
        val userToInsert =
            if (isNewAddress) {
                val id = addressDao.insert(mapper.toAddressEntity(user.address))
                user.copy(address = user.address.copy(id = id))
            } else {
                user
            }
        userDao.insertOrUpdate(mapper.toUserEntity(userToInsert))
    }

    override suspend fun loadAddresses(): List<Address> =
        addressDao.loadAll().map { mapper.toAddress(it) }
}