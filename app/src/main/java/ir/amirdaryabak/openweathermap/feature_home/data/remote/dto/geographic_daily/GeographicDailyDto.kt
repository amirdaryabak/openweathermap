package ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic_daily

import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic_daily.GeographicDailyEntity

data class GeographicDailyDto(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val current: Current,
    val daily: List<DailyDto>
)

fun GeographicDailyDto.toGeographicDailyEntity(): GeographicDailyEntity {
    return GeographicDailyEntity(
        lat = lat,
        lon = lon,
        timezone = timezone,
        dailyList = daily.map { it.toDailyEntity() }
    )
}