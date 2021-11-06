package ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic_daily

import java.io.Serializable

data class TempEntity(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
): Serializable