package com.ahndwon.gethub.api.model

data class Issue(val filter: String,
                 val state: String,
                 val labels: List<Labels>,
                 val user: UserData,
                 val title: String,
                 val body: String,
                 val repo: Repo) {
    data class Labels(val name: String,
                      val url: String,
                      val description: String)
}