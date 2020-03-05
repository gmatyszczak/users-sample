package pl.gmat.users.common.mapper

import pl.gmat.users.common.database.entity.AddressEntity
import pl.gmat.users.common.database.entity.UserEntity
import pl.gmat.users.common.model.Address
import pl.gmat.users.common.model.Gender
import pl.gmat.users.common.model.User
import javax.inject.Inject

interface UserMapper {

    fun toUser(userEntity: UserEntity, addressEntities: List<AddressEntity>): User
    fun toAddress(addressEntity: AddressEntity): Address
    fun toUserEntity(user: User): UserEntity
    fun toAddressEntity(address: Address, userId: Long): AddressEntity
}

class UserMapperImpl @Inject constructor() : UserMapper {

    override fun toUser(userEntity: UserEntity, addressEntities: List<AddressEntity>) = User(
        id = userEntity.id,
        firstName = userEntity.firstName,
        lastName = userEntity.lastName,
        age = userEntity.age,
        gender = Gender.valueOf(userEntity.gender),
        addresses = addressEntities.map { toAddress(it) }
    )

    override fun toAddress(addressEntity: AddressEntity) = Address(
        id = addressEntity.id,
        value = addressEntity.value
    )

    override fun toUserEntity(user: User) = UserEntity(
        id = user.id,
        firstName = user.firstName,
        lastName = user.lastName,
        age = user.age,
        gender = user.gender.name
    )

    override fun toAddressEntity(address: Address, userId: Long) = AddressEntity(
        id = address.id,
        value = address.value,
        userId = userId
    )
}