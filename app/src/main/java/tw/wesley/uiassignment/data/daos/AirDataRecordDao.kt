package tw.wesley.uiassignment.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tw.wesley.uiassignment.data.entities.AirDataRecordEntity

@Dao
interface AirDataRecordDao {

    @Query("SELECT * FROM air_data_records")
    fun getAllRecords(): List<AirDataRecordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(records: List<AirDataRecordEntity>)

    @Delete
    suspend fun delete(record: AirDataRecordEntity)
}