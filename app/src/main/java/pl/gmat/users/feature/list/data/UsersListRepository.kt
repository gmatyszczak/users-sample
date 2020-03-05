package pl.gmat.users.feature.list.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.gmat.users.common.database.dao.AddressDao
import pl.gmat.users.common.database.dao.UserDao
import pl.gmat.users.common.mapper.UserMapper
import pl.gmat.users.common.model.User
import javax.inject.Inject

interface UsersListRepository {

    fun loadUsers(): Flow<List<User>>
    suspend fun deleteUsers()
}

class UsersListRepositoryImpl @Inject constructor(
    private val mapper: UserMapper,
    private val userDao: UserDao,
    private val addressDao: AddressDao
) : UsersListRepository {

    override fun loadUsers(): Flow<List<User>> =
        userDao.loadAll()
            .map { userEntities ->
                userEntities.map { userEntity ->
                    mapper.toUser(userEntity, addressDao.loadForUserId(userEntity.id))
                }
            }

    override suspend fun deleteUsers() = userDao.deleteAll()
}