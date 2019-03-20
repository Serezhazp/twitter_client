package com.serezhazp.twitterclient.data.actions

import java.io.File

interface UploadMediaAction {

    fun uploadMedia(file: File, function: (mediaId: String, error: Throwable?) -> Unit)
}