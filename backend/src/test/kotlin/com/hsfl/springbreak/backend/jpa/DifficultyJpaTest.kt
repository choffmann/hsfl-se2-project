package com.hsfl.springbreak.backend.jpa

import com.hsfl.springbreak.backend.controller.DifficultyController
import com.hsfl.springbreak.backend.entity.DifficultyEntity
import com.hsfl.springbreak.backend.repository.DifficultyRepository
import junit.framework.TestCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Description
import javax.transaction.Transactional


@Transactional
@SpringBootTest

class DifficultyJpaTest {

        @Autowired
        private lateinit var repository: DifficultyRepository

        @Autowired
        private lateinit var controller: DifficultyController

        val DifficultyList = listOf(
                DifficultyEntity(
                        name ="Easy"
                )

        )

        @Description("Add  Difficulties to database")
        @BeforeEach
        fun saveCategoryInDB() {
            repository.saveAll( DifficultyList)
        }


        @Test
        @Description("Test get all difficulties in database")
        fun retrieveDifficulties() {
            val apiResponse = controller.getEntries()
            TestCase.assertTrue(apiResponse.success)
            TestCase.assertEquals(4, apiResponse.data?.size ?: -1)
        }

    }
