package ir.amirdaryabak.openweathermap.feature_home.domain.use_case

import ir.amirdaryabak.openweathermap.core.data_source.remote.ExceptionParser
import ir.amirdaryabak.openweathermap.core.data_source.remote.Resource
import ir.amirdaryabak.openweathermap.feature_home.data.remote.dto.geographic.toGeographicEntity
import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic.GeographicEntity
import ir.amirdaryabak.openweathermap.feature_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWeatherByCityNameUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    operator fun invoke(
        cityName: String
    ): Flow<Resource<GeographicEntity>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getGeographicByCityName(cityName).toGeographicEntity()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(ExceptionParser.getErrorMessage<GeographicEntity>(t))
        }
    }
}