package ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic

data class SysEntity(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)