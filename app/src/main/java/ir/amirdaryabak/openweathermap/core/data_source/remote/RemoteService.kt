package ir.amirdaryabak.openweathermap.core.data_source.remote

import ir.amirdaryabak.openweathermap.core.utils.Constants
import ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic.GeographicDto
import ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic_daily.GeographicDailyDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RemoteService {

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }

    @GET("weather")
    suspend fun getGeographicByCoordinates(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = Constants.API_KEY,
    ): GeographicDto

    @GET("weather")
    suspend fun getGeographicByCityName(
        @Query("cityName") cityName: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = Constants.API_KEY,
    ): GeographicDto

    @GET("onecall")
    suspend fun getGeographicDailyByCoordinates(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = Constants.API_KEY,
    ): GeographicDailyDto

}