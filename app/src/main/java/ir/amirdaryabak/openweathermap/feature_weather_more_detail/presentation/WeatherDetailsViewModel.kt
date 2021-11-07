package ir.amirdaryabak.openweathermap.feature_weather_more_detail.presentation

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
class WeatherDetailsViewModel @Inject constructor() : ViewModel() {

    sealed class HandleEvent {

    }

    private val _tasksEvent = MutableSharedFlow<HandleEvent>()
    val tasksEvent: SharedFlow<HandleEvent> = _tasksEvent


    fun onEvent(event: HandleEvent) =
        viewModelScope.launch() {

        }

}