package ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic

import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic.WindEntity


data class WindDto(
    val deg: Int,
    val gust: Double,
    val speed: Double
)

fun WindDto.toWindEntity(): WindEntity {
    return WindEntity(
        deg = deg,
        gust = gust,
        speed = speed
    )
}