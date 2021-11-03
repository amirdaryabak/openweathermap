package ir.amirdaryabak.openweathermap.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.amirdaryabak.openweathermap.core.data_source.remote.RemoteService
import ir.amirdaryabak.openweathermap.feature_home.data.repository.HomeRepositoryImpl
import ir.amirdaryabak.openweathermap.feature_home.domain.repository.HomeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideHomeRepository(api: RemoteService): HomeRepository {
        return HomeRepositoryImpl(api)
    }

}