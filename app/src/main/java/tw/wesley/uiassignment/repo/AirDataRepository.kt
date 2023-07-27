package tw.wesley.uiassignment.repo

import android.content.SharedPreferences
import timber.log.Timber
import tw.wesley.uiassignment.data.database.AirDataRecordDao
import tw.wesley.uiassignment.data.mappers.AirDataRecordMapper
import tw.wesley.uiassignment.network.AirQualityApiService
import java.util.Calendar
import javax.inject.Inject

class AirDataRepository @Inject constructor(
    private val apiService: AirQualityApiService,
    private val airDataRecordDao: AirDataRecordDao,
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        const val LAST_FETCH_TIME = "last_fetch_time"
    }

    /**
     * Fetches the latest air quality data from the API and stores it into the local database.
     * This function should be called when the application needs to refresh the air quality data.
     * Note: Before fetching the data, check if the data needs to be refreshed by using the method 'needToFetchData()'
     * @param force: force refresh the data despite the time constrain
     */
    suspend fun fetchAndStoreAirQualityData(force: Boolean = false) {
        val lastFetchHour = sharedPreferences.getInt(LAST_FETCH_TIME, -1)
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        if (force || lastFetchHour != currentHour) {
            // Only fetch new data if the last fetch was at least an hour ago.
            val response = apiService.getAirQualityData().records
            Timber.d("dataFromApi/totalSiteCounts=${response.size}")
            airDataRecordDao.insertAll(AirDataRecordMapper.toEntityList(response))

            // Update the last fetch time.
            with(sharedPreferences.edit()) {
                putInt(LAST_FETCH_TIME, currentHour)
                apply()
            }
        } else {
            Timber.d("You already have latest data stored in DB, you can queryAirData() directly")
        }
    }

    fun getListAirDataFlow() = airDataRecordDao.getAllRecordsAsFlow()

    suspend fun queryAirData(keyword: String) = airDataRecordDao.queryAirData(keyword)
}
