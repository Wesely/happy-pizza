package tw.wesley.uiassignment.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import tw.wesley.uiassignment.network.AirQualityApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // tell hilt how to generate a Retrofit instance
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://data.epa.gov.tw/")
            .build()
    }

    // tell hilt how to generate a AirQualityApiService instance
    @Provides
    @Singleton
    fun provideAirQualityApiService(retrofit: Retrofit): AirQualityApiService {
        return retrofit.create(AirQualityApiService::class.java)
    }
}
