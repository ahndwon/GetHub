package com.ahndwon.gethub.api.model

import com.google.gson.annotations.SerializedName


data class UserData(val login: String,
                    val name: String,
                    @field:SerializedName("repos_url")
                    val reposUrl: String,
                    @field:SerializedName("avatar_url")
                    val avatarUrl: String,
                    val email: String,
                    val followers: Int,
                    val following: Int,
                    val url: String)