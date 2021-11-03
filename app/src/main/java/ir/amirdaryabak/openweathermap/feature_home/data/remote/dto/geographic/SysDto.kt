package ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic

import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic.SysEntity


data class SysDto(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)

fun SysDto.toSysEntity(): SysEntity {
    return SysEntity(
        country = country,
        id = id,
        sunrise = sunrise,
        sunset = sunset,
        type = type,
    )
}