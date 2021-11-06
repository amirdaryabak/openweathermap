package ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic_daily

import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic.WeatherEntity
import java.io.Serializable

data class DailyEntity(
    val temp: TempEntity,
    val humidity: Int,
    val wind_speed: Double,
    val weatherList: List<WeatherEntity>,
    val rain: Double,
): Serializable