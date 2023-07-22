package tw.wesley.uiassignment.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import tw.wesley.uiassignment.network.AirQualityApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // tell hilt how to generate a Retrofit instance
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://data.epa.gov.tw/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    // tell hilt how to generate a AirQualityApiService instance
    @Provides
    @Singleton
    fun provideAirQualityApiService(retrofit: Retrofit): AirQualityApiService {
        return retrofit.create(AirQualityApiService::class.java)
    }
}
