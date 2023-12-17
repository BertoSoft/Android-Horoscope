package com.bertosoft.horoscapp.domain.model

//
// Data class, que usamos para traducir los datros de la capa data, de predictionResponse
//
data class PredictionModel(
    val horoscope: String,
    val sign: String,
)
