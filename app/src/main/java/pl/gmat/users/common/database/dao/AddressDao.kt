package pl.gmat.users.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.gmat.users.common.database.entity.ADDRESS_TABLE_NAME
import pl.gmat.users.common.database.entity.AddressEntity

@Dao
interface AddressDao {

    @Insert
    suspend fun insert(address: AddressEntity)

    @Query("SELECT * FROM $ADDRESS_TABLE_NAME WHERE userId = :userId")
    fun loadForUserId(userId: Long): Flow<List<AddressEntity>>

    @Query("DELETE FROM $ADDRESS_TABLE_NAME WHERE userId = :userId")
    suspend fun deleteForUserId(userId: Long)

    @Query("DELETE FROM $ADDRESS_TABLE_NAME")
    suspend fun deleteAll()
}