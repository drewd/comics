package com.dobson.comics.repository.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComicDataWrapper(
    val data: ComicDataContainer?
)
