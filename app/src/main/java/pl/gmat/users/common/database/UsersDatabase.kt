package pl.gmat.users.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.gmat.users.common.database.dao.AddressDao
import pl.gmat.users.common.database.dao.UserDao
import pl.gmat.users.common.database.entity.AddressEntity
import pl.gmat.users.common.database.entity.UserEntity

private const val DATABASE_VERSION = 3

@Database(
    entities = [
        UserEntity::class,
        AddressEntity::class
    ],
    version = DATABASE_VERSION
)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun addressDao(): AddressDao
}