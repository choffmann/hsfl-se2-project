package com.hsfl.springbreak.backend.repository

import com.hsfl.springbreak.backend.entity.CategoryEntity
import org.springframework.data.repository.CrudRepository

interface CategoryRepository: CrudRepository<CategoryEntity, Long>