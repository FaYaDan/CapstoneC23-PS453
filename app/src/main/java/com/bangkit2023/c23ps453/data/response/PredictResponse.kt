package com.bangkit2023.c23ps453.data.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("prediction")
	val prediction: String
)
