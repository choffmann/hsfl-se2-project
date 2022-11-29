package com.hsfl.springbreak.backend.repository

import com.hsfl.springbreak.backend.entity.RatingEntity
import org.springframework.data.repository.CrudRepository

interface RatingRepository: CrudRepository<RatingEntity, Long> {
}