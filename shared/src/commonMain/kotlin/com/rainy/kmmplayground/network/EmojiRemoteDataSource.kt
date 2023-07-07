package com.rainy.kmmplayground.network

import com.rainy.kmmplayground.domain.model.Emoji
import com.rainy.kmmplayground.domain.model.toDomain
import com.rainy.kmmplayground.network.model.EmojiResponse
import com.rainy.kmmplayground.network.resources.AllEmojisResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get

class EmojiRemoteDataSource(
    private val client: HttpClient
) {
    suspend fun getEmojis(): List<Emoji> {
        return client.get(AllEmojisResource()).body<List<EmojiResponse>>()
            .map(EmojiResponse::toDomain)
    }
}