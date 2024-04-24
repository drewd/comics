package com.dobson.comics.repository.remote

import com.dobson.comics.model.Comic
import com.dobson.comics.repository.Result
import com.dobson.comics.repository.remote.service.ComicService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ComicRemoteDataSource @Inject constructor(private val service: ComicService) {

    suspend fun get(id: Int): Result<Comic> {
        return withContext(Dispatchers.IO) {
            try {
                val comic = service.get(id).data?.results?.first()
                if (comic == null) {
                    // Expand the error scenarios based upon the APIs spec.
                    // Return the specific error from the API
                    Result.Error(IllegalStateException("Unknown comic"))
                } else {
                    Result.Success(comic)
                }
            } catch (e: Exception) {
                // Wrap this exception in something meaningful to the UI
                Result.Error(e)
            }
        }
    }
}