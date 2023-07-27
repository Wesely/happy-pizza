package tw.wesley.uiassignment.repo

import timber.log.Timber
import tw.wesley.uiassignment.data.database.AirDataRecordDao
import tw.wesley.uiassignment.data.mappers.AirDataRecordMapper
import tw.wesley.uiassignment.network.AirQualityApiService
import javax.inject.Inject

class AirDataRepository @Inject constructor(
    private val apiService: AirQualityApiService,
    private val airDataRecordDao: AirDataRecordDao
) {
    suspend fun fetchAndStoreAirQualityData() {
        val response = apiService.getAirQualityData().records
        Timber.d("dataFromApi/totalSiteCounts=${response.size}")
        airDataRecordDao.insertAll(AirDataRecordMapper.toEntityList(response))
    }

    fun getListAirDataFlow() = airDataRecordDao.getAllRecordsAsFlow()

    suspend fun queryAirData(keyword: String) = airDataRecordDao.queryAirData(keyword)
}