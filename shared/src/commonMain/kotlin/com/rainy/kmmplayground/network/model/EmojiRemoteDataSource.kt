package com.rainy.kmmplayground.network.model

import io.ktor.client.HttpClient
import io.ktor.client.request.get

class EmojiRemoteDataSource(
    private val client: HttpClient
) {

    suspend fun getEmojis() {
        client.get {
            url {

            }
        }
    }
}