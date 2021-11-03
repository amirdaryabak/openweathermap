package ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic


data class GeographicEntity(
    val coordinates: CoordinatesEntity,
    val weatherList: List<WeatherEntity>,
    val main: MainEntity,
    val wind: WindEntity,
    val sys: SysEntity,
    val id: Int,
    val name: String,
    val cod: Int
)