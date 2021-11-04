package ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic_daily

import ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic.WeatherDto
import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic_daily.DailyEntity

data class DailyDto(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val moonrise: Int,
    val moonset: Int,
    val moon_phase: Double,
    val temp: TempDto,
    val feels_like: FeelsLike,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Double,
    val wind_speed: Double,
    val wind_deg: Int,
    val wind_gust: Double,
    val weather: List<WeatherDto>,
    val clouds: Int,
    val pop: Double,
    val rain: Double,
    val uvi: Double
)

fun DailyDto.toDailyEntity(): DailyEntity {
    return DailyEntity(
        temp = temp.toTempEntity(),
        humidity = humidity,
        wind_speed = wind_speed,
        weatherList = weather,
        rain = rain
    )
}