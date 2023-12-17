package com.bertosoft.horoscapp.data

import android.util.Log
import com.bertosoft.horoscapp.data.network.HoroscopeApiService
import com.bertosoft.horoscapp.data.network.response.PredictionResponse
import com.bertosoft.horoscapp.domain.Repository
import com.bertosoft.horoscapp.domain.model.PredictionModel
import javax.inject.Inject

//
// Esta clase es para la comunicacion con la capa dominio , con la funcion getPrediction() de Repository en la capa dominio
// de hecho extiende de la interfaz Repository de la capa dominio
//
class RepositoryImpl @Inject constructor(private val apiService: HoroscopeApiService):Repository {
    override suspend fun getPrediction(sign: String): PredictionModel? {
        //
        // LLamar a Retrofit
        //
        runCatching { apiService.getHoroscope(sign) }
            .onSuccess { return it.toDomain() } // Retornamos it que es un PredictionResponse, que to domain transforma en PredictionModel
            .onFailure { Log.i("aris", "Ha ocurrido un error ${it.message}")}
        return null // Si no ha sido success, enttonces retorna null
    }
}