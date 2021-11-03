package ir.amirdaryabak.openweathermap.feature_home.data.repository

import ir.amirdaryabak.openweathermap.core.data_source.remote.RemoteService
import ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic.GeographicDto
import ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic_daily.GeographicDailyDto
import ir.amirdaryabak.openweathermap.feature_home.domain.repository.HomeRepository

class HomeRepositoryImpl(
    private val api: RemoteService
): HomeRepository {

    override suspend fun getGeographicByCoordinates(lat: String, lon: String): GeographicDto =
        api.getGeographicByCoordinates(lat = lat, lon = lon)

    override suspend fun getGeographicByCityName(cityName: String): GeographicDto =
        api.getGeographicByCityName(cityName)

    override suspend fun getGeographicDailyByCoordinates(
        lat: String,
        lon: String
    ): GeographicDailyDto = api.getGeographicDailyByCoordinates(lat, lon)

}