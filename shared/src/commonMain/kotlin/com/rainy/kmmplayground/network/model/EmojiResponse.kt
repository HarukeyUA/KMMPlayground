package com.rainy.kmmplayground.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmojiResponse(
    @SerialName("category")
    val category: String?,
    @SerialName("group")
    val group: String?,
    @SerialName("htmlCode")
    val htmlCode: List<String>?,
    @SerialName("name")
    val name: String?,
    @SerialName("unicode")
    val unicode: List<String>?
)