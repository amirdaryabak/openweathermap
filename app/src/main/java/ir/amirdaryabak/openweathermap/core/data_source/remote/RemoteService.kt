package ir.amirdaryabak.openweathermap.core.data_source.remote

import ir.amirdaryabak.openweathermap.core.utils.Constants
import ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic.GeographicDto
import ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic_daily.GeographicDailyDto
import retrofit2.http.GET
import retrofit2.http.Path


interface RemoteService {

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }

    @GET("weather?lat={lat}&lon={lon}&units={units}&appid={key}")
    suspend fun getGeographicByCoordinates(
        @Path("lat") lat: String,
        @Path("lon") lon: String,
        @Path("units") units: String = "metric",
        @Path("key") key: String = Constants.API_KEY,
    ): GeographicDto

    @GET("weather?q={cityName}&units={units}&appid={key}")
    suspend fun getGeographicByCityName(
        @Path("cityName") cityName: String,
        @Path("units") units: String = "metric",
        @Path("key") key: String = Constants.API_KEY,
    ): GeographicDto

    @GET("onecall?lat={lat}&lon={lon}&units={units}&appid={key}")
    suspend fun getGeographicDailyByCoordinates(
        @Path("lat") lat: String,
        @Path("lon") lon: String,
        @Path("units") units: String = "metric",
        @Path("key") key: String = Constants.API_KEY,
    ): GeographicDailyDto

}