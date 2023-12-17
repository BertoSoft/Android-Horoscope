package com.bertosoft.horoscapp.domain

import com.bertosoft.horoscapp.domain.model.PredictionModel

//
// Esta interface es la comunicacion entre la capa data y la capa dominio
//
interface Repository {
    suspend fun getPrediction(sign:String): PredictionModel?
}