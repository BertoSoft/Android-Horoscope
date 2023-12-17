package com.bertosoft.horoscapp.data.network

import com.bertosoft.horoscapp.BuildConfig.BASE_URL
import com.bertosoft.horoscapp.data.RepositoryImpl
import com.bertosoft.horoscapp.data.core.interceptor.AuthInterceptor
import com.bertosoft.horoscapp.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//
// Modulo necesario para poder injectar retrofit en getPrediction
//
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //
    // Llamada a retrofit, que luego puede ser injectada con daggerhilt
    //
    @Provides
    @Singleton  // Esta etiqueta es para que daggerhilt no cree esta interface, cada vez que sea llamada
    fun providerRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //
    // Funcion que nos provee de okHttpClient para los interceptadores
    //
    @Provides
    @Singleton
    fun providesOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    //
    // Funcion que hacemos para poder ejecutar la interface HoroscopeApiService
    //
    @Provides
    fun providerHoroscopeApiService(retrofit: Retrofit): HoroscopeApiService {
        return retrofit.create(HoroscopeApiService::class.java)
    }

    //
    // Para poder injectar un repository en getusescase de dominio
    //
    @Provides
    fun provideRepository(apiService: HoroscopeApiService): Repository {
        return RepositoryImpl(apiService)
    }
}