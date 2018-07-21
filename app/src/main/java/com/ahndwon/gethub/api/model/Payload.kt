package com.ahndwon.gethub.api.model

import com.google.gson.annotations.SerializedName

data class Payload(val action: String,
        val forkee: Forkee) {

    // ForkEvent
    data class Forkee(val id: String,
                      @field:SerializedName("full_name")
                      val fullName: String,
                      val url: String)
}