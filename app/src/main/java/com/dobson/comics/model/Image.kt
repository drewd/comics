package com.dobson.comics.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    val path: String?,
    val extension: String?,
) {
    val url: String?
        get() {
            return path?.run {
                plus(extension?.let { ".${it}" })
            }
        }
}
