package com.farn.booksapp.network

import com.farn.booksapp.models.Volume
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CustomVolumeDeserializer : JsonDeserializer<Volume> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Volume {
         val volume : JsonElement = json.asJsonObject.get("volumeInfo")
         return Gson().fromJson(volume, Volume::class.java)


    }

}