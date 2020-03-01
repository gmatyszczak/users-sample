package pl.gmat.users.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.gmat.users.common.database.entity.ADDRESS_TABLE_NAME
import pl.gmat.users.common.database.entity.AddressEntity

@Dao
interface AddressDao {

    @Insert
    suspend fun insert(address: AddressEntity): Long

    @Query("SELECT * FROM $ADDRESS_TABLE_NAME")
    suspend fun loadAll(): List<AddressEntity>

    @Query("SELECT * FROM $ADDRESS_TABLE_NAME WHERE id = :id")
    suspend fun load(id: Long): AddressEntity
}