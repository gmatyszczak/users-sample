package pl.gmat.users.common.model

data class User(
    val id: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val age: String = "",
    val gender: Gender = Gender.MALE,
    val address: Address = Address()
)