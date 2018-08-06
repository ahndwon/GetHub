package com.ahndwon.gethub.api.dao

import com.google.gson.annotations.SerializedName

data class Payload(val action: String,
                   val forkee: Forkee,
                   val member: Member,
                   val repo: Repo) {
    data class Repo(
            val id: Long,
            val name: String,
            val url: String)
    // ForkEvent
    data class Forkee(val id: String,
                      @field:SerializedName("full_name")
                      val fullName: String,
                      val url: String)

    data class Member(val login: String,
                      val url: String)
}