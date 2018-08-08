package com.ahndwon.gethub.api.dao

import com.google.gson.annotations.SerializedName

data class ReadMe(val name: String,
                  @field:SerializedName("download_url")
                  val downloadUrl: String)