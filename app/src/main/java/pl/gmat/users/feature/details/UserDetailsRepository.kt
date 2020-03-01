package pl.gmat.users.feature.details

import pl.gmat.users.common.database.dao.AddressDao
import pl.gmat.users.common.database.dao.UserDao
import pl.gmat.users.common.mapper.UserMapper
import javax.inject.Inject

interface UserDetailsRepository

class UserDetailsRepositoryImpl @Inject constructor(
    private val mapper: UserMapper,
    private val userDao: UserDao,
    private val addressDao: AddressDao
) : UserDetailsRepository