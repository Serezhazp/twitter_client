package com.serezhazp.twitterclient.domain

import java.io.File

data class TweetToPost(val text: String, val file: File? = null, var media: String? = "")