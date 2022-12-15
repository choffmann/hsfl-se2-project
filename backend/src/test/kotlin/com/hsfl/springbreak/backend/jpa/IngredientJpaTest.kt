package com.hsfl.springbreak.backend.jpa

import com.hsfl.springbreak.backend.controller.IngredientController
import com.hsfl.springbreak.backend.controller.RecipeController
import com.hsfl.springbreak.backend.model.IngredientRecipe
import com.hsfl.springbreak.backend.model.Recipe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Description
import javax.transaction.Transactional


@Transactional
@SpringBootTest
class IngredientJpaTest{
    @Autowired
    private lateinit var controllerRecipe: RecipeController

    @Autowired
    private lateinit var controllerIngredient: IngredientController


    private var recipeId: Long = 0


    val recipe = Recipe.CreateRecipe(
            title = "Schockomuffins",
            shortDescription = "Diese Muffins lieben alle! So schnell und einfach vorzubereiten und unglaublich saftig – das ist unser beliebtes Schokomuffin-Rezept.",
            price = 5.00,
            duration = 10.00,
            difficultyId = 2,
            categoryId =  1,
            creatorId = 1,
            longDescription = "null",
            ingredients = listOf(
                    IngredientRecipe.WithoutRecipe(
                            ingredientName = "Öl",
                            unit = "liter",
                            amount = 400
                    ),
                    IngredientRecipe.WithoutRecipe(
                            ingredientName = "OrangenSaft",
                            unit = "liter",
                            amount = 4000
                    )
            )
    )


    @Description("Add Recipe to database")
    @BeforeEach
    fun saveData() {
        recipeId = controllerRecipe.createRecipe(recipe).data?.id ?: 0;
    }

    // Test get All Ingredients
    @Test
    fun getCorrectAllIngredients() {

       val apiResponse = controllerIngredient.getAllIngredients()
        assertTrue(apiResponse.success)
        apiResponse .data?.let { assertEquals(35, it.size) }
        apiResponse .data?.let { assertEquals("Wasser", it[32].name) }
        apiResponse .data?.let { assertEquals("Apfel", it[0].name) }
        apiResponse .data?.let { assertEquals("Öl", it[33].name) }
    }

}