package com.ahndwon.gethub.api

import com.ahndwon.gethub.api.dao.*
import retrofit2.Call
import retrofit2.http.*

interface GithubApi {
    @FormUrlEncoded
    @POST("login/oauth/access_token")
    @Headers("Accept: application/json")
    fun getAccessToken(@Field("client_id") clientId: String,
                       @Field("client_secret") clientSecret: String,
                       @Field("code") code: String): Call<Auth>

    @GET("users/ahndwon/received_events")
    fun getEvents(): Call<List<Event>>

    @GET("user/issues")
    fun getIssue(@Query("filter") filter: String,
                 @Query("state") state: String): Call<List<Issue>>

    @GET("user")
    fun getUser(): Call<UserData>

    @GET("user/repos")
    fun getUserRepos(@Query("sort") sort: String): Call<List<Repo>>

    @GET("repos/{id}/{repoName}")
    fun getRepo(@Path("id") id: String,
                @Path("repoName") repoName: String): Call<Repo>

    @GET("repos/{id}/{repo}/contents/{path}")
    fun getContents(@Path("id") id: String,
                    @Path("repo") repo: String,
                    @Path("path") path: String): Call<List<Content>>

    @GET("repos/{id}/{repoName}/readme")
    fun getReadMe(@Path("id") id: String,
                  @Path("repoName") repoName: String): Call<ReadMe>
}