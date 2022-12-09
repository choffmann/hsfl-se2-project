package com.hsfl.springbreak.backend.jpa

import com.hsfl.springbreak.backend.controller.RecipeController
import com.hsfl.springbreak.backend.entity.*
import com.hsfl.springbreak.backend.model.IngredientRecipe
import com.hsfl.springbreak.backend.model.Recipe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Description
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import javax.transaction.Transactional

@Transactional
@SpringBootTest
class RecipeJpaTest {

    @Autowired
    private lateinit var controller: RecipeController
    private var id: Long = 0
    private var testRecipe = Recipe.CreateRecipe(
        title = "Schockomuffins",
        shortDescription = "Diese Muffins lieben alle! So schnell und einfach vorzubereiten und unglaublich saftig – das ist unser beliebtes Schokomuffin-Rezept.",
        price = 5.00,
        duration = 10.00,
        categoryId = 2,
        creatorId = 2,
        createTime = LocalDate.now(),
        difficultyId = 2,
        image = "null",
        longDescription = "null",
        ingredients = listOf(IngredientRecipe.WithoutRecipe(
            ingredientName = "Milch",
            unit = "liter",
            amount = 400))
    )

    @BeforeEach
    @Description("Add testRecipe to database")
    fun postRecipe(){
        val apiResponse = controller.createNewRecipe(testRecipe.toDto())
        id = apiResponse.data!!.id
    }

    @Test
    @Description("Test correct RecipeController.findRecipeById(id) with existing id")
    fun testCorrectGetRecipe(){
        val apiResponse = controller.getRecipeById(id)
        assertEquals(true, apiResponse.success)
    }

    @Test
    @Description("Test faulty RecipeController.findRecipeById(id) with non-existing id")
    fun testFaultyGetRecipe() {
        val apiResponse = controller.getRecipeById(id + 1)
        assertEquals(false, apiResponse.success)
    }
    @Test
    @Description("")
    fun testCorrectPutRecipe() {
        val testRecipe2 = testRecipe
        testRecipe2.title = "new title"

        val apiResponse = controller.updateRecipe(id, testRecipe2.toDto())
        assertEquals(true, apiResponse.success)
        assertEquals("new title", apiResponse.data?.title ?: "")
    }

    @Test
    @Description("")
    fun testFaultyPutRecipe() {
        val apiResponse = controller.updateRecipe(id + 1, testRecipe.toDto())
        assertEquals(false, apiResponse.success)
    }

    @Test
    @Description("Test correct RecipeController.deleteRecipe(id) with existing id")
    fun testCorrectDeleteRecipe() {
        controller.deleteRecipe(id)
        val apiResponse = controller.getRecipeById(id)
        assertEquals(false, apiResponse.success)
    }

    @Test
    @Description("Test faulty RecipeController.deleteRecipe(id) with non-existing id")
    fun testFaultyDeleteRecipe() {
        org.junit.jupiter.api.assertThrows<ResponseStatusException> {
            controller.deleteRecipe(id + 1)
        }
    }
}