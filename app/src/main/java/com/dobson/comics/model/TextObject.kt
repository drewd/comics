package com.dobson.comics.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TextObject(
    val type: String? = null,
    val language: String? = null,
    val text: String? = null,
)