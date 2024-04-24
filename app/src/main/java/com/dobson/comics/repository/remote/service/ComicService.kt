package com.dobson.comics.repository.remote.service

import com.dobson.comics.repository.remote.model.ComicDataWrapper
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicService {
    @GET("/v1/public/comics/{comicId}")
    suspend fun get(@Path("comicId") id: Int): ComicDataWrapper
}