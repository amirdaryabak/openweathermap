package ir.amirdaryabak.openweathermap.feature_home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.amirdaryabak.openweathermap.core.data_source.remote.Resource
import ir.amirdaryabak.openweathermap.core.utils.exhaustive
import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic.GeographicEntity
import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic_daily.GeographicDailyEntity
import ir.amirdaryabak.openweathermap.feature_home.domain.use_case.HomeUseCases
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
) : ViewModel() {

    sealed class HandleEvent {
        object ShowLoading : HandleEvent()
        data class GetWeatherByCoordinates(val lat: String, val lon: String) : HandleEvent()
        data class OnGetWeatherByCoordinatesSuccess(val geographicEntity: GeographicEntity?) :
            HandleEvent()
        data class OnGetWeatherByCoordinatesError(val errorMessage: String?) : HandleEvent()

        data class GetWeatherDaily(val lat: String, val lon: String) : HandleEvent()
        data class OnGetWeatherDailySuccess(val geographicDailyEntity: GeographicDailyEntity?) :
            HandleEvent()
        data class OnGetWeatherDailyError(val errorMessage: String?) : HandleEvent()

        data class GetWeatherByCityName(val cityName: String) : HandleEvent()
        data class OnGetWeatherByCityNameSuccess(val geographicEntity: GeographicEntity?) :
            HandleEvent()
        data class OnGetWeatherByCityNameError(val errorMessage: String?) : HandleEvent()
    }

    private val _tasksEvent = MutableSharedFlow<HandleEvent>()
    val tasksEvent: SharedFlow<HandleEvent> = _tasksEvent


    fun onEvent(event: HandleEvent) =
        viewModelScope.launch() {
            when (event) {
                is HandleEvent.ShowLoading -> {
                    _tasksEvent.emit(HandleEvent.ShowLoading)
                }
                is HandleEvent.GetWeatherByCoordinates -> getWeatherByCoordinates(
                    event.lat,
                    event.lon
                )
                is HandleEvent.OnGetWeatherByCoordinatesSuccess -> {
                    _tasksEvent.emit(
                        HandleEvent.OnGetWeatherByCoordinatesSuccess(event.geographicEntity)
                    )
                }
                is HandleEvent.OnGetWeatherByCoordinatesError -> {
                    _tasksEvent.emit(
                        HandleEvent.OnGetWeatherByCoordinatesError(event.errorMessage)
                    )
                }

                is HandleEvent.ShowLoading -> {
                    _tasksEvent.emit(HandleEvent.ShowLoading)
                }
                is HandleEvent.GetWeatherDaily -> getWeatherDaily(
                    event.lat,
                    event.lon
                )
                is HandleEvent.OnGetWeatherDailySuccess -> {
                    _tasksEvent.emit(
                        HandleEvent.OnGetWeatherDailySuccess(event.geographicDailyEntity)
                    )
                }
                is HandleEvent.OnGetWeatherDailyError -> {
                    _tasksEvent.emit(
                        HandleEvent.OnGetWeatherDailyError(event.errorMessage)
                    )
                }

                is HandleEvent.GetWeatherByCityName -> getWeatherByCityName(event.cityName,)
                is HandleEvent.OnGetWeatherByCityNameSuccess -> {
                    _tasksEvent.emit(
                        HandleEvent.OnGetWeatherByCityNameSuccess(event.geographicEntity)
                    )
                }
                is HandleEvent.OnGetWeatherByCityNameError -> {
                    _tasksEvent.emit(
                        HandleEvent.OnGetWeatherByCityNameError(event.errorMessage)
                    )
                }
            }.exhaustive
        }

    private fun getWeatherByCoordinates(lat: String, lon: String) {
        homeUseCases.getWeatherByCoordinatesUseCase(lat, lon).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    onEvent(HandleEvent.OnGetWeatherByCoordinatesSuccess(result.data))
                }
                is Resource.Error -> {
                    onEvent(HandleEvent.OnGetWeatherByCoordinatesError(result.message))
                }
                is Resource.Loading -> {
                    onEvent(HandleEvent.ShowLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getWeatherDaily(lat: String, lon: String) {
        homeUseCases.getWeatherDailyUseCase(lat, lon).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    onEvent(HandleEvent.OnGetWeatherDailySuccess(result.data))
                }
                is Resource.Error -> {
                    onEvent(HandleEvent.OnGetWeatherDailyError(result.message))
                }
                is Resource.Loading -> {
                    onEvent(HandleEvent.ShowLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getWeatherByCityName(cityName: String) {
        homeUseCases.getWeatherByCityNameUseCase(cityName).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    onEvent(HandleEvent.OnGetWeatherByCityNameSuccess(result.data))
                }
                is Resource.Error -> {
                    onEvent(HandleEvent.OnGetWeatherByCityNameError(result.message))
                }
                is Resource.Loading -> {
                    onEvent(HandleEvent.ShowLoading)
                }
            }
        }.launchIn(viewModelScope)
    }

}