package tw.wesley.uiassignment.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tw.wesley.uiassignment.data.local.AirData

@Dao
interface AirDataRecordDao {

    @Query("SELECT * FROM air_data_records")
    fun getAllRecords(): List<AirData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(records: List<AirData>)

    @Delete
    suspend fun delete(record: AirData)
}