package ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic

import com.google.gson.annotations.SerializedName
import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic.GeographicEntity

data class GeographicDto(
    @SerializedName("coord")
    val coordinates: CoordinatesDto,
    @SerializedName("weather")
    val weatherList: List<WeatherDto>,
    val base: String,
    val main: MainDto,
    val visibility: Int,
    val wind: WindDto,
    val clouds: Clouds,
    val dt: Int,
    val sys: SysDto,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)

fun GeographicDto.toGeographicEntity(): GeographicEntity {
    return GeographicEntity(
        coordinates = coordinates.toCoordinatesEntity(),
        weatherList = weatherList.map { it.toWeatherEntity() },
        main = main.toMainEntity(),
        wind = wind.toWindEntity(),
        sys = sys.toSysEntity(),
        id = id,
        name = name,
        cod = cod
    )
}