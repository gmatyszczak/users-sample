package pl.gmat.users.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.gmat.users.common.database.entity.USER_TABLE_NAME
import pl.gmat.users.common.database.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM $USER_TABLE_NAME")
    fun loadAll(): Flow<List<UserEntity>>
}