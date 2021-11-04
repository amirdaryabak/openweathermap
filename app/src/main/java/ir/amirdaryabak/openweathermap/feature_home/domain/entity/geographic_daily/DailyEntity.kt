package ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic_daily

import ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic.WeatherDto

data class DailyEntity(
    val temp: TempEntity,
    val humidity: Int,
    val wind_speed: Double,
    val weatherList: List<WeatherDto>,
    val rain: Double,
)