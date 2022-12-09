package com.hsfl.springbreak.backend.repository

import com.hsfl.springbreak.backend.entity.FavoriteEntity
import org.springframework.data.repository.CrudRepository

interface FavoriteRepository : CrudRepository<FavoriteEntity, Long>
