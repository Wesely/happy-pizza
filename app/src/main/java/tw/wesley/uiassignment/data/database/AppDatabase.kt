package tw.wesley.uiassignment.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import tw.wesley.uiassignment.data.local.AirData

@Database(entities = [AirData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun airDataRecordDao(): AirDataRecordDao
}