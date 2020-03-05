package pl.gmat.users

import pl.gmat.users.common.database.entity.AddressEntity
import pl.gmat.users.common.database.entity.UserEntity
import pl.gmat.users.common.model.Address
import pl.gmat.users.common.model.Gender
import pl.gmat.users.common.model.User

const val firstName = "firstName"
const val lastName = "lastName"
const val age = "age"
const val addressValue = "addressValue"

val testAddress = Address(
    id = 10,
    value = addressValue
)

val testUser = User(
    id = 10,
    firstName = firstName,
    lastName = lastName,
    age = age,
    gender = Gender.FEMALE,
    addresses = listOf(testAddress)
)

val testAddressEntity = AddressEntity(
    id = 10,
    value = addressValue,
    userId = 10
)

val testUserEntity = UserEntity(
    id = 10,
    firstName = firstName,
    lastName = lastName,
    age = age,
    gender = Gender.FEMALE.name
)