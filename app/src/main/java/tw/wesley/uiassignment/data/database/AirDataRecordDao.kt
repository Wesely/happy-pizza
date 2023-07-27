package tw.wesley.uiassignment.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tw.wesley.uiassignment.data.local.AirData

@Dao
interface AirDataRecordDao {

    @Query("SELECT * FROM air_data_records")
    fun getAllRecords(): List<AirData>

    @Query("SELECT * FROM air_data_records")
    fun getAllRecordsAsFlow(): Flow<List<AirData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(records: List<AirData>)

    @Delete
    suspend fun delete(record: AirData)


    /**
     * @param keyword 對 site_name, county 進行部分匹配搜尋
     * 使用 '%' 允許部分匹配，例: 南，可以匹配 台南、南化
     * 使用 || 做Query字串傳接，例: 輸入 "南" 則串接成 "%南%"
     */
    @Query("SELECT * FROM air_data_records WHERE site_name LIKE '%' || :keyword || '%' OR county LIKE '%' || :keyword || '%'")
    suspend fun queryAirData(keyword: String): List<AirData>
}