package tw.wesley.uiassignment.models

import com.squareup.moshi.Json

data class AirDataResponse(
    @Json(name = "records") val records: List<AirDataRecord>
)