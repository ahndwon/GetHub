package com.ahndwon.gethub.api.model

import com.google.gson.annotations.SerializedName

data class Issue(val filter: String,
                 val state: String,
                 val labels: List<Labels>,
                 val user: UserData,
                 val title: String,
                 val body: String,
                 val repo: Repo,
                 @field:SerializedName("pull_request")
                 val pullRequest: PullRequest) {
    data class Labels(val name: String,
                      val url: String,
                      val description: String)

    data class PullRequest(val url: String)
}