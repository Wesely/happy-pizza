package tw.wesley.uiassignment.network

import retrofit2.http.GET
import retrofit2.http.Query
import tw.wesley.uiassignment.data.remote.AirDataResponse


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
    ): AirDataResponse
}