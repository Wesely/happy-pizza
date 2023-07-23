package tw.wesley.uiassignment.data.remote

import com.squareup.moshi.Json

data class AirDataResponse(
    @Json(name = "records") val records: List<AirDataRecord>
)