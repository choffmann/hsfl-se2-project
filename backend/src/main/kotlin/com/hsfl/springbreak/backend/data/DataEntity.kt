package com.hsfl.springbreak.backend.data

interface DataEntity<D, T> {
    fun toDto(): D
    fun fromDto(dto: D): T
}