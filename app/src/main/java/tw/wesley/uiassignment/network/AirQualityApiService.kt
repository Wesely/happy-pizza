package tw.wesley.uiassignment.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * CWB stands for 中央氣象局,
 * this interface defines the method that requires data from CWB
 */
interface AirQualityApiService {

    /**
     * HttpGet from https://data.epa.gov.tw/api/v2/aqx_p_432?limit=1000&api_key=cebebe84-e17d-4022-a28f-81097fda5896&sort=ImportDate%20desc&format=json
     */
    @GET("/api/v2/aqx_p_432")
    suspend fun getAirQualityData(
        @Query("limit") limit: Int = 1000,
        @Query("api_key") apiKey: String = "cebebe84-e17d-4022-a28f-81097fda5896",
        @Query("sort") sort: String = "ImportDate desc",
        @Query("format") format: String = "json"
    ): ResponseBody // TODO: change to data object in https://github.com/Wesely/happy-pizza/issues/3
}