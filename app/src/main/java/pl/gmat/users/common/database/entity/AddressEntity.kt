package pl.gmat.users.common.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val ADDRESS_TABLE_NAME = "addresses"

@Entity(tableName = ADDRESS_TABLE_NAME)
data class AddressEntity(
    @PrimaryKey val id: Int = 0,
    val value: String = ""
)