package ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic

data class WeatherEntity(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)