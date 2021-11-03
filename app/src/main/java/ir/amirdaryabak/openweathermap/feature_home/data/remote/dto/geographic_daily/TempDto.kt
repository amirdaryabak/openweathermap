package ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic_daily

import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic_daily.TempEntity

data class TempDto(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

fun TempDto.toTempEntity(): TempEntity {
    return TempEntity(
        day = day,
        min = min,
        max = max,
        night = night,
        eve = eve,
        morn = morn
    )
}