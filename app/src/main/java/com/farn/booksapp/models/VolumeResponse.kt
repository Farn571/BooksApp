package com.farn.booksapp.models

import com.google.gson.annotations.SerializedName

data class VolumeResponse(
   @SerializedName("items")
   val volumes : List<Volume>,
   val error: Throwable?
)
