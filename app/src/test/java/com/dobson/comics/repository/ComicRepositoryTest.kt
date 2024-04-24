package com.dobson.comics.repository

import com.dobson.comics.model.Comic
import com.dobson.comics.repository.remote.ComicRemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ComicRepositoryTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var mockComicRemoteDataSource: ComicRemoteDataSource

    @MockK
    lateinit var mockResult: Result<Comic>

    @InjectMockKs
    lateinit var repository: ComicRepository

    @Before
    fun setUp() {
        coEvery { mockComicRemoteDataSource.get(any()) } returns mockResult
    }

    @Test
    fun get() = runBlocking {
        assertEquals(repository.get(1), mockResult)
        coVerify { mockComicRemoteDataSource.get(1) }
    }
}