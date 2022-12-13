package com.hsfl.springbreak.backend.service

import com.hsfl.springbreak.backend.repository.RatingRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class RatingJpaService(val ratingRepository: RatingRepository) {


    private fun calcScore() {

    }
}