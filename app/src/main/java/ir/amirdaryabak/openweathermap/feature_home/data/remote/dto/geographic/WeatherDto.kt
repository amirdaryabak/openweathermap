package ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic

import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic.WeatherEntity

data class WeatherDto(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

fun WeatherDto.toWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        id = id,
        main = main,
        description = description,
        icon = icon,
    )
}