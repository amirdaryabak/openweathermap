package ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic

import java.io.Serializable

data class WeatherEntity(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
): Serializable