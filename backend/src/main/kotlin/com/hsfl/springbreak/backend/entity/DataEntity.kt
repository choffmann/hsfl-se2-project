package com.hsfl.springbreak.backend.entity

interface DataEntity<D, T> {
    fun toDto(): D
    fun fromDto(dto: D): T
}