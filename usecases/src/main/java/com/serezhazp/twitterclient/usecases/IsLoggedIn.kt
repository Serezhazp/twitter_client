package com.serezhazp.twitterclient.usecases

import com.serezhazp.twitterclient.data.SessionRepository

class IsLoggedIn(private val sessionRepository: SessionRepository) {

    operator fun invoke(): Boolean = sessionRepository.isLoggedIn()
}