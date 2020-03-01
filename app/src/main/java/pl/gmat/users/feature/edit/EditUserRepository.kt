package pl.gmat.users.feature.edit

import pl.gmat.users.common.model.Address
import pl.gmat.users.common.model.User
import javax.inject.Inject

interface EditUserRepository {

    suspend fun addUser(user: User, isNewAddress: Boolean = false)
    suspend fun loadAddresses(): List<Address>
}

class EditUserRepositoryImpl @Inject constructor() : EditUserRepository {

    override suspend fun addUser(user: User, isNewAddress: Boolean) = Unit
    override suspend fun loadAddresses(): List<Address> = emptyList()
}