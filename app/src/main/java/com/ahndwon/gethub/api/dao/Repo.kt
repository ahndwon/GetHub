package com.ahndwon.gethub.api.dao

import com.google.gson.annotations.SerializedName


data class Repo(val id: Long,
                val name: String,
                val url: String,
                val language: String,
                val private: Boolean,
                @field:SerializedName("stargazers_count")
                val stargazersCount: Int,
                @field:SerializedName("forks_count")
                val forksCount: Int,
                @field:SerializedName("watchers_count")
                val watchersCount: Int,
                @field:SerializedName("updated_at")
                val updatedAt: String,
                @field:SerializedName("created_at")
                val createdAt: String)
