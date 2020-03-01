package pl.gmat.users.common.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val USER_TABLE_NAME = "users"

@Entity(tableName = USER_TABLE_NAME)
data class UserEntity(
    @PrimaryKey val id: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val age: String = "",
    val gender: Int = 0,
    val address: Int = 0
)