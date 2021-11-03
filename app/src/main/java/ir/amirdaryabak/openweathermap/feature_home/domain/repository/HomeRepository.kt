package ir.amirdaryabak.openweathermap.feature_home.domain.repository

import ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic.GeographicDto
import ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic_daily.GeographicDailyDto

interface HomeRepository {

    suspend fun getGeographicByCoordinates(lat: String, lon: String): GeographicDto

    suspend fun getGeographicByCityName(cityName: String): GeographicDto

    suspend fun getGeographicDailyByCoordinates(lat: String, lon: String): GeographicDailyDto

}