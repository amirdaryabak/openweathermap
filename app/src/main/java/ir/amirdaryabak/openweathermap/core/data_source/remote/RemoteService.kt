package ir.amirdaryabak.openweathermap.core.data_source.remote

import ir.amirdaryabak.openweathermap.core.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path


interface RemoteService {

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }

    @GET("weather?lat={lat}&lon={lon}&appid={key}")
    suspend fun getGeographicByCoordinates(
        @Path("lat") lat: String,
        @Path("lon") lon: String,
        @Path("key") key: String = Constants.API_KEY,
    )

    @GET("weather?q={cityName}&appid={key}")
    suspend fun getGeographicByCityName(
        @Path("cityName") cityName: String,
        @Path("key") key: String = Constants.API_KEY,
    )

    @GET("onecall?lat={lat}&lon={lon}&exclude={part}&appid={key}")
    suspend fun getGeographicDailyByCoordinates(
        @Path("lat") lat: String,
        @Path("lon") lon: String,
        @Path("part") part: String = "daily",
        @Path("key") key: String = Constants.API_KEY,
    )

}