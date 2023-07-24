package tw.wesley.uiassignment.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "air_data_records")
data class AirData(
    @PrimaryKey @ColumnInfo(name = "site_id") val siteId: String,
    @ColumnInfo(name = "site_name") val siteName: String,
    @ColumnInfo(name = "county") val county: String,
    @ColumnInfo(name = "pm2_5") val pm25: Int,
    @ColumnInfo(name = "status") val status: String
)