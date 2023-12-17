package com.bertosoft.horoscapp.data.network.response

import com.bertosoft.horoscapp.domain.model.PredictionModel
import com.google.gson.annotations.SerializedName

data class PredictionResponse(
    @SerializedName("date") val date: String,
    @SerializedName("horoscope") val horoscope: String,
    @SerializedName("sign") val sign: String,
) {
    fun toDomain(): PredictionModel {
        //
        // Creamos un obcjeto PrediccionModel y le pasamos los datos de PredictionResponse
        //
        return PredictionModel(
            horoscope = horoscope,
            sign = sign,
        )
    }
}