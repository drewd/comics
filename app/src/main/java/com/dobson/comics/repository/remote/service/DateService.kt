package com.dobson.comics.repository.remote.service

import java.util.Date
import javax.inject.Inject

class DateService @Inject constructor() {

    fun now(): Long {
        return Date().time
    }
}