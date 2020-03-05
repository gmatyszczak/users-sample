package pl.gmat.users.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.gmat.users.common.database.entity.USER_TABLE_NAME
import pl.gmat.users.common.database.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(user: UserEntity)

    @Query("SELECT * FROM $USER_TABLE_NAME")
    fun loadAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM $USER_TABLE_NAME WHERE id = :id")
    fun load(id: Long): Flow<UserEntity?>

    @Query("DELETE FROM $USER_TABLE_NAME WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM $USER_TABLE_NAME")
    suspend fun deleteAll()
}