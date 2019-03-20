package com.serezhazp.twitterclient.domain

import java.io.Serializable

data class TwitterUser(val userName: String, val nickName: String, val avatar: String) :
    Serializable