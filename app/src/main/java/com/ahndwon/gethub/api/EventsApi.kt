package com.ahndwon.gethub.api

import com.ahndwon.gethub.api.model.Event
import retrofit2.Call
import retrofit2.http.GET

interface EventsApi {
    @GET("users/ahndwon/received_events")
    fun getEvents(): Call<List<Event>>
}