package tw.wesley.uiassignment.repo

import timber.log.Timber
import tw.wesley.uiassignment.data.daos.AirDataRecordDao
import tw.wesley.uiassignment.data.mappers.AirDataRecordMapper
import tw.wesley.uiassignment.network.AirQualityApiService
import javax.inject.Inject

class AirDataRepository @Inject constructor(
    private val apiService: AirQualityApiService,
    private val airDataRecordDao: AirDataRecordDao
) {
    suspend fun fetchAndStoreAirQualityData() {
        val response = apiService.getAirQualityData().records
        Timber.d("dataFromApi/${response.joinToString(separator = "\n") { "${it.county}-${it.siteName}: ${it.status}" }}")
        airDataRecordDao.insertAll(AirDataRecordMapper.toEntityList(response))
    }
}