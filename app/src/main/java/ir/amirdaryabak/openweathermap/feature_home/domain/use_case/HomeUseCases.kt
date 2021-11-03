package ir.amirdaryabak.openweathermap.feature_home.domain.use_case

data class HomeUseCases(
    val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    val getWeatherByCoordinatesUseCase: GetWeatherByCoordinatesUseCase,
    val getWeatherDailyUseCase: GetWeatherDailyUseCase,
)
