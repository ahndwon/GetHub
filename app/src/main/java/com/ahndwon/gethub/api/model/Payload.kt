package com.ahndwon.gethub.api.model

import com.google.gson.annotations.SerializedName

data class Payload(val action: String,
                   val forkee: Forkee,
                   val member: Member,
                   val repo: Repo) {
    // ForkEvent
    data class Forkee(val id: String,
                      @field:SerializedName("full_name")
                      val fullName: String,
                      val url: String)

    data class Member(val login: String,
                      val url: String)
}