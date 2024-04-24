package com.dobson.comics.repository.remote.model

import com.dobson.comics.model.Comic
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComicDataContainer(
    val results: List<Comic>?,
)
