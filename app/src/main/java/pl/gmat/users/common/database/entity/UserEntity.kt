package pl.gmat.users.common.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.gmat.users.common.model.Gender

const val USER_TABLE_NAME = "users"

@Entity(tableName = USER_TABLE_NAME)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val age: String = "",
    val gender: String = Gender.MALE.name,
    val addressId: Long = 0
)