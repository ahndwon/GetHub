package com.ahndwon.gethub.utils

import android.app.Application
import android.content.res.AssetManager
import com.ahndwon.gethub.model.LangColor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStreamReader

class CustomApplication: Application() {
    val colorMap by lazy {
        getLangColorMap(resources.assets)
    }

    private fun getLangColorMap(assetManager: AssetManager) : Map<String, LangColor> {
        try {
            val source = assetManager.open("LanguageColor.json")
            val gson = Gson()
            val reader = InputStreamReader(source)
            return gson.fromJson(reader, object : TypeToken<Map<String, LangColor>>() {}.type)
        } catch (exception: IOException) {
            throw IOException("LanguageColor.json file not found")
        }
    }
}

