package ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic


data class MainEntity(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int,
)