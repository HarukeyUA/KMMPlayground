package com.rainy.kmmplayground.domain.model

import com.rainy.kmmplayground.network.model.EmojiResponse

data class Emoji(
    val category: String,
    val group: String,
    val htmlCode: String,
    val name: String,
    val unicode: String
)

internal fun EmojiResponse.toDomain(): Emoji {
    return Emoji(
        category = category ?: "",
        group = group ?: "",
        htmlCode = htmlCode?.firstOrNull() ?: "",
        name = name ?: "",
        unicode = unicode?.firstOrNull() ?: ""
    )
}
