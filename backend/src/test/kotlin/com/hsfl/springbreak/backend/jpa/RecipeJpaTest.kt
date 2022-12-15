package com.hsfl.springbreak.backend.jpa

import com.hsfl.springbreak.backend.controller.RecipeController
import com.hsfl.springbreak.backend.model.*
import com.hsfl.springbreak.backend.service.RecipeService
import junit.framework.TestCase.*
import org.junit.Assert.assertNotEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Description
import javax.transaction.Transactional


@Transactional
@SpringBootTest
class RecipeTest {
    @Autowired
    private lateinit var service: RecipeService

    @Autowired
    private lateinit var controller: RecipeController

    private var recipeId: Long = 0
    val recipe = listOf(
            Recipe.CreateRecipe(
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
                            ingredientName = "Milch",
                            unit = "liter",
                            amount = 400
                    )
                )
            ),
             Recipe.CreateRecipe(
             title = "Käsemakkaroni",
            shortDescription = "Die genialsten Käsemakkaroni.",
            price = 10.00,
            duration = 15.00,
            difficultyId = 2,
            categoryId =  3,
            creatorId = 2,
            longDescription = "Salzwasser zum Kochen bringen und die Makkaroni nach Packungsanleitung kochen, lieber etwas kürzer als zu lange, dann abgießen.\n" +
                    "Inzwischen die Petersilie waschen, trocken schleudern und hacken, (1 Paket TK-Petersilie geht natürlich auch). Den Knoblauch abziehen und fein hacken.",
            ingredients = listOf(
                    IngredientRecipe.WithoutRecipe(
                            ingredientName = "Sahne",
                            unit = "gramm",
                            amount = 200
                    ),
                    IngredientRecipe.WithoutRecipe(
                            ingredientName = "Makkaroni, kurze",
                            unit = "gramm",
                            amount = 250
                    )
            )
    )
    )

    @BeforeEach
    @Description("Add Recipe to database")
    fun postRecipe() {
       recipeId = controller.createRecipe(recipe[0]).data?.id ?: 0;
        var recipeId2 = controller.createRecipe(recipe[1]).data?.id ?: 0;
    }

    @Test
    fun createRecipe() {
        val apiResponse = service.createRecipe(recipe[0])
        Assertions.assertTrue(apiResponse.success)
    }
    @Test
    fun testCorrectGetRecipe() {
        val apiResponse = service.getRecipeById(recipeId)
        Assertions.assertTrue(apiResponse.success)

    }
    @Test
    fun testFailedGetRecipe() {
        val apiResponse = service.getRecipeById(recipeId + 10)
        Assertions.assertFalse(apiResponse.success)

    }
    @Test
    fun getRecipeByName() {
        val apiResponse = service.getRecipeByName(recipe[0].title)
        Assertions.assertTrue(apiResponse.success)

    }
    @Test
    @Description("")
    fun testCorrectPutRecipe() {
        var recipe2 =Recipe.ChangeRecipe(
                recipeId=recipeId,
                title = "new title",
                shortDescription = "Diese Muffins lieben alle! So schnell und einfach vorzubereiten und unglaublich saftig – das ist unser beliebtes Schokomuffin-Rezept.",
                price = 5.00,
                duration = 10.00,
                difficultyId = 2,
                categoryId =  1,
                longDescription = "null",
                ingredients = listOf(
                        IngredientRecipe.WithoutRecipe(
                                ingredientName = "Milch",
                                unit = "liter",
                                amount = 400
                        )
                )
        )

        val apiResponse = service.updateRecipe(recipe2)
        assertTrue( apiResponse.success)
        assertEquals("new title", apiResponse.data?.title ?: "")
    }
    @Test
    @Description("")
    fun testFaultyPutRecipe() {
        var recipe2 =Recipe.ChangeRecipe(
                recipeId=recipeId,
                title = "title",
                shortDescription = "Diese Muffins lieben alle! So schnell und einfach vorzubereiten und unglaublich saftig – das ist unser beliebtes Schokomuffin-Rezept.",
                price = 5.00,
                duration = 10.00,
                difficultyId = 2,
                categoryId =  1,
                longDescription = "null",
                ingredients = listOf(
                        IngredientRecipe.WithoutRecipe(
                                ingredientName = "Milch",
                                unit = "liter",
                                amount = 400
                        )
                )
        )

        val apiResponse = service.updateRecipe(recipe2)
        assertTrue( apiResponse.success)
        assertNotEquals("new title", apiResponse.data?.title ?: "")
    }
    @Test
    fun testDeleteRecipe() {
        val apiResponse=service.deleteRecipeById(recipeId)
        assertTrue( apiResponse.success)
        val apiResponse2=service.deleteRecipeById(recipeId+20)
        assertFalse( apiResponse2.success)
    }

    @Test
    fun getRecipes() {
        val apiResponse=service.getRecipes()
        assertTrue( apiResponse.success)
        assertEquals(4, apiResponse.data?.size ?: -1)
    }
    @Test
    fun getRecipesByCreator(){
        val apiResponse=service.getRecipesByCreator(2)
        assertTrue(apiResponse.success)
        assertEquals("Käsemakkaroni", apiResponse.data?.get(0)?.title ?: "")
    }




}