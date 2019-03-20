package com.serezhazp.twitterclient.domain

import java.io.Serializable

data class Tweet(
    val text: String,
    val createdAt: String = "",
    val user: TwitterUser,
    val media: String? = null,
    val hashTags: List<String>? = emptyList()
) : Serializable