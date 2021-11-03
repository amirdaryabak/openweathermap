package ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic_daily

data class FeelsLike(
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)