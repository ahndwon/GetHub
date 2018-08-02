package com.ahndwon.gethub.api.model

import com.google.gson.annotations.SerializedName

data class Repo (val id: Long,
                 val name: String,
                 val url: String,
                 val language: String,
                 @field:SerializedName("stargazers_count")
                 val stargazersCount: Int)