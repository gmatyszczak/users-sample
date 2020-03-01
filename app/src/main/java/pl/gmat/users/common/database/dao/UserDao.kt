package pl.gmat.users.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import pl.gmat.users.common.database.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: UserEntity)
}