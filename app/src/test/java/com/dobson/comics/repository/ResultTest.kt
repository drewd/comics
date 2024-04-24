package com.dobson.comics.repository

import org.junit.Assert.assertEquals
import org.junit.Test

class ResultTest {

    @Test
    fun successOr_successWithData() {
        val result = "GOOD"
        assertEquals(result, Result.Success(result).successOr("BAD"))
    }

    @Test
    fun successOr_successWithoutData() {
        val fallback = "FALLBACK"
        assertEquals(fallback, Result.Success(null).successOr(fallback))
    }

    @Test
    fun successOr_error() {
        val fallback = "FALLBACK"
        assertEquals(fallback, Result.Error(Exception()).successOr(fallback))
    }
}