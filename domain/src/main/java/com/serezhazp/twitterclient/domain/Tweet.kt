package com.serezhazp.twitterclient.domain

import java.io.Serializable

data class Tweet(
    var text: String,
    val createdAt: String = "",
    val user: TwitterUser,
    var media: String? = null,
    val hashTags: List<String>? = emptyList()
) : Serializable