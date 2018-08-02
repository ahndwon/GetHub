package com.ahndwon.gethub.api.model

import com.google.gson.annotations.SerializedName

data class Event(val id: String,
                 @field:SerializedName("type")
                 val watchType: String,
                 val actor: Actor,
                 val repo: Repo,
                 val public: Boolean,
                 val payload: Payload,
                 @field:SerializedName("created_at")
                 val createdAt: String) {
    data class Repo(
            val id: Long,
            val name: String,
            val url: String)
}
