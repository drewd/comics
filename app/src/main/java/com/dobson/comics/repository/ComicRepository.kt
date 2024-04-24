package com.dobson.comics.repository

import com.dobson.comics.model.Comic
import com.dobson.comics.repository.remote.ComicRemoteDataSource
import javax.inject.Inject

class ComicRepository @Inject constructor(private val remoteDataSource: ComicRemoteDataSource) {
    suspend fun get(id: Int): Result<Comic> {
        return remoteDataSource.get(id)
    }
}