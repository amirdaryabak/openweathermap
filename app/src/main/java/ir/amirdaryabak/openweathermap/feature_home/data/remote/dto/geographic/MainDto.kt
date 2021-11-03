package ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic

import com.google.gson.annotations.SerializedName
import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic.MainEntity

data class MainDto(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int,
)

fun MainDto.toMainEntity(): MainEntity {
    return MainEntity(
        temp = temp,
        feelsLike = feelsLike,
        tempMin = tempMin,
        tempMax = tempMax,
        pressure = pressure,
        humidity = humidity,
    )
}