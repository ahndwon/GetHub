package com.ahndwon.gethub.api.dao

import com.google.gson.annotations.SerializedName

data class Content(val type: String,
                   val encoding: String,
                   val size: Int,
                   val name: String,
                   val path: String,
                   val sha: String,
                   val url: String,
                   @field:SerializedName("download_url")
                   val downloadUrl: String)
