package com.ahndwon.gethub.api

import com.ahndwon.gethub.api.model.Issue
import com.ahndwon.gethub.api.model.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IssueApi {
    @GET("user/issues")
    fun getIssue(@Query("filter") filter: String, @Query("state") state: String): Call<List<Issue>>
}