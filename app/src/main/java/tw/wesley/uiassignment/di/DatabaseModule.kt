package tw.wesley.uiassignment.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tw.wesley.uiassignment.data.database.AirDataRecordDao
import tw.wesley.uiassignment.data.database.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "air_data_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAirDataRecordDao(database: AppDatabase): AirDataRecordDao {
        return database.airDataRecordDao()
    }
}