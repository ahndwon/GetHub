package com.ahndwon.gethub.api.model

import com.google.gson.annotations.SerializedName

data class Event(val id: String,
                 val watchType: String,
                 val actor: Actor,
                 val repo: Repo,
                 val public: Boolean,
                 @field:SerializedName("created_at")
                 val createdAt: String
                 )