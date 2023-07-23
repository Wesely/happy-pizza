package tw.wesley.uiassignment.data.mappers

import tw.wesley.uiassignment.data.entities.AirDataRecordEntity
import tw.wesley.uiassignment.data.remote.AirDataRecord

object AirDataRecordMapper {
    fun toEntity(airDataRecord: AirDataRecord): AirDataRecordEntity {
        return AirDataRecordEntity(
            siteId = airDataRecord.siteId ?: "",
            siteName = airDataRecord.siteName,
            county = airDataRecord.county,
            pm25 = airDataRecord.pm25,
            status = airDataRecord.status
        )
    }

    fun toEntityList(airDataRecords: List<AirDataRecord>): List<AirDataRecordEntity> {
        return airDataRecords.map { toEntity(it) }
    }
}