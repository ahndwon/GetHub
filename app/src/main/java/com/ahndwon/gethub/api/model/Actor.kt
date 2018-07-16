package com.ahndwon.gethub.api.model

import com.google.gson.annotations.SerializedName

data class Actor(val id: Long,
                 val login: String,
                 val url: String,
                 @field:SerializedName("avatar_url")
                 val avatarUrl: String)