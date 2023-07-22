package tw.wesley.uiassignment.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AirDataRecord(
    @Json(name = "siteid") val siteId: String?,
    @Json(name = "sitename") val siteName: String,
    @Json(name = "county") val county: String,
    @Json(name = "pm2.5") val pm25: String,
    @Json(name = "status") val status: String
)

/** Original data format
 * Ref: https://data.epa.gov.tw/api/v2/aqx_p_432?limit=1000&api_key=cebebe84-e17d-4022-a28f-81097fda5896&sort=ImportDate%20desc&format=json
{
"sitename": "萬華",
"county": "臺北市",
"aqi": "37",
"pollutant": "",
"status": "良好",
"so2": "0.6",
"co": "0.47",
"o3": "23",
"o3_8hr": "33.2",
"pm10": "13",
"pm2.5": "13",
"no2": "22.1",
"nox": "23.8",
"no": "1.6",
"wind_speed": "0.4",
"wind_direc": "132",
"publishtime": "2023/07/22 18:00:00",
"co_8hr": "0.3",
"pm2.5_avg": "11.3",
"pm10_avg": "16",
"so2_avg": "0",
"longitude": "121.507972",
"latitude": "25.046503",
"siteid": "13"
}

Requirement:
「顯示欄位」： API 欄位
「序號」： SiteId
「站名」：SiteName
「縣市」：County
「PM 2.5」：PM2.5
「狀態」：Status
 */

