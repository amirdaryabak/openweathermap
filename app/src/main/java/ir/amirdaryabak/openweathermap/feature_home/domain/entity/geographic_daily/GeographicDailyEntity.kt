package ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic_daily

data class GeographicDailyEntity(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val dailyList: List<DailyEntity>
)