package com.dobson.comics.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comic(
    val id: Int,
    val title: String? = null,
    val description: String? = null,
    val thumbnail: Image? = null,
    val textObjects: List<TextObject>? = null,
)
