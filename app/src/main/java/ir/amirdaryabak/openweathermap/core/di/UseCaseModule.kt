package ir.amirdaryabak.openweathermap.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.amirdaryabak.openweathermap.feature_home.domain.repository.HomeRepository
import ir.amirdaryabak.openweathermap.feature_home.domain.use_case.GetWeatherByCityNameUseCase
import ir.amirdaryabak.openweathermap.feature_home.domain.use_case.GetWeatherByCoordinatesUseCase
import ir.amirdaryabak.openweathermap.feature_home.domain.use_case.GetWeatherDailyUseCase
import ir.amirdaryabak.openweathermap.feature_home.domain.use_case.HomeUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideHomeUseCases(repository: HomeRepository): HomeUseCases {
        return HomeUseCases(
            getWeatherByCityNameUseCase = GetWeatherByCityNameUseCase(repository),
            getWeatherByCoordinatesUseCase = GetWeatherByCoordinatesUseCase(repository),
            getWeatherDailyUseCase = GetWeatherDailyUseCase(repository),
        )
    }

}








