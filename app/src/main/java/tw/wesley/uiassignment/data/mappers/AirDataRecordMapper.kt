package tw.wesley.uiassignment.data.mappers

import tw.wesley.uiassignment.data.local.AirData
import tw.wesley.uiassignment.data.local.AirData.Companion.INVALID_INT
import tw.wesley.uiassignment.data.remote.RemoteAirDataRecord

/**
 * We cannot change the data format/type of the remote data,
 * this mapper convert remote format [RemoteAirDataRecord] to our local format [AirData]
 */
object AirDataRecordMapper {
    private fun toLocalAirData(remoteAirDataRecord: RemoteAirDataRecord): AirData {
        // We don't have logic to handle invalid PM2.5 records, I decided to mark them as INVALID_INT
        val processedPM25 = try {
            remoteAirDataRecord.pm25.toInt()
        } catch (e: NumberFormatException) {
            INVALID_INT
        }

        return AirData(
            siteId = remoteAirDataRecord.siteId,
            siteName = remoteAirDataRecord.siteName,
            county = remoteAirDataRecord.county,
            pm25 = processedPM25,
            status = remoteAirDataRecord.status
        )
    }

    fun toEntityList(remoteAirDataRecords: List<RemoteAirDataRecord>): List<AirData> {
        return remoteAirDataRecords.map { toLocalAirData(it) }
    }
}