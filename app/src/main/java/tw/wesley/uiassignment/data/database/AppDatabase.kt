package tw.wesley.uiassignment.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import tw.wesley.uiassignment.data.daos.AirDataRecordDao
import tw.wesley.uiassignment.data.entities.AirDataRecordEntity

@Database(entities = [AirDataRecordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun airDataRecordDao(): AirDataRecordDao
}