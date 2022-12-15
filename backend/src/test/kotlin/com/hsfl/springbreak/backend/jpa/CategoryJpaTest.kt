package com.hsfl.springbreak.backend.jpa

import com.hsfl.springbreak.backend.controller.CategoryController
import com.hsfl.springbreak.backend.entity.CategoryEntity
import com.hsfl.springbreak.backend.repository.CategoryRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

@Transactional
@SpringBootTest
class CategoryJpaTest {

    @Autowired
    private lateinit var repository: CategoryRepository

    @Autowired
    private lateinit var controller: CategoryController

    val CategoryList = listOf(
            CategoryEntity(
                    name ="ve"
            ),
            CategoryEntity(
                    name ="vg"
            ),
            CategoryEntity(
                    name ="Bio"
            ),
            CategoryEntity(
                    name ="Dessert"
            )

    )
    @BeforeEach
    fun saveCategoryInDB() {
        repository.saveAll(CategoryList)
    }

    @Test
    fun retrieveCategories() {
        val apiResponse = controller.retrieveCategories()
        assertTrue(apiResponse.success)
        assertEquals(17, apiResponse.data?.size ?: -1)
    }


}