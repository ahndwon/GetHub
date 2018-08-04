package com.ahndwon.gethub.utils

import android.content.res.Resources
import com.ahndwon.gethub.api.model.LangColor
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader

object LangColor {

}


fun getColor(language: String) : String {
    val gson = GsonBuilder().create()

    val asset = Resources.getSystem().assets.open("LanguageColor.json")
    val bufferedReader = BufferedReader(InputStreamReader(asset))

//    val colors = gson.fromJson(reader, LangColor::class.java)
    val colorMap: Map<String, LangColor> = gson.fromJson(JsonObject(), object : TypeToken<Map<String, LangColor>>() {}.type)

    return colorMap[language]?.color ?: ""
}