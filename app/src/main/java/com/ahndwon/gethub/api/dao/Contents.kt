package com.ahndwon.gethub.api.dao

import com.google.gson.annotations.SerializedName

data class Contents (
        val name: String,
        val path: String,
        val sha: String,
        val size: Int,
        val url: String,
        @field:SerializedName("download_url")
        val downloadUrl: String,
        val type: String
)