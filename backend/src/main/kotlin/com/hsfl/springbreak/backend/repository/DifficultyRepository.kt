package com.hsfl.springbreak.backend.repository

import com.hsfl.springbreak.backend.entity.DifficultyEntity
import org.springframework.data.repository.CrudRepository

interface DifficultyRepository : CrudRepository<DifficultyEntity, Long>