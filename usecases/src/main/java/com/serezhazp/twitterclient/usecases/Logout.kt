package com.serezhazp.twitterclient.usecases

import com.serezhazp.twitterclient.data.SessionRepository

open class Logout(private val sessionRepository: SessionRepository) {

    operator fun invoke() = sessionRepository.logout()
}