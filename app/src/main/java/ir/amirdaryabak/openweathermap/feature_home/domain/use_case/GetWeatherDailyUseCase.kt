package ir.amirdaryabak.openweathermap.feature_home.domain.use_case

import ir.amirdaryabak.openweathermap.core.data_source.remote.ExceptionParser
import ir.amirdaryabak.openweathermap.core.data_source.remote.Resource
import ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic.toGeographicEntity
import ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic_daily.toGeographicDailyEntity
import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic.GeographicEntity
import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic_daily.GeographicDailyEntity
import ir.amirdaryabak.openweathermap.feature_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWeatherDailyUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    operator fun invoke(
        lat: String, lon: String
    ): Flow<Resource<GeographicDailyEntity>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getGeographicDailyByCoordinates(lat, lon).toGeographicDailyEntity()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(ExceptionParser.getErrorMessage<GeographicDailyEntity>(t))
        }
    }
}