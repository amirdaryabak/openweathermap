package ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic

import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic.CoordinatesEntity

data class CoordinatesDto(
    val lat: Int,
    val lon: Int
)

fun CoordinatesDto.toCoordinatesEntity(): CoordinatesEntity {
    return CoordinatesEntity(
        lat = lat,
        lon = lon
    )
}