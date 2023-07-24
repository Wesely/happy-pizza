package tw.wesley.uiassignment.data.mappers

import tw.wesley.uiassignment.data.local.AirData
import tw.wesley.uiassignment.data.remote.RemoteAirDataRecord

/**
 * We cannot change the data format/type of the remote data,
 * this mapper convert remote format [RemoteAirDataRecord] to our local format [AirData]
 */
object AirDataRecordMapper {
    private fun toLocalAirData(remoteAirDataRecord: RemoteAirDataRecord): AirData {
        return AirData(
            siteId = remoteAirDataRecord.siteId,
            siteName = remoteAirDataRecord.siteName,
            county = remoteAirDataRecord.county,
            pm25 = remoteAirDataRecord.pm25.toInt(),
            status = remoteAirDataRecord.status
        )
    }

    fun toEntityList(remoteAirDataRecords: List<RemoteAirDataRecord>): List<AirData> {
        return remoteAirDataRecords.filter {
            // We don't have logic to handle invalid PM2.5 records, filter them out here to make sure our PM2.5 are all valid numbers
            it.pm25.isNotEmpty() && it.pm25.matches("\\d+".toRegex())
        }.map { toLocalAirData(it) }
    }
}