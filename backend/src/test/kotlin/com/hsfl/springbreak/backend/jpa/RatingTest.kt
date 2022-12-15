package com.hsfl.springbreak.backend.jpa

import com.hsfl.springbreak.backend.controller.RatingController
import com.hsfl.springbreak.backend.controller.RecipeController
import com.hsfl.springbreak.backend.controller.UserController
import com.hsfl.springbreak.backend.model.IngredientRecipe
import com.hsfl.springbreak.backend.model.Rating
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.model.User
import com.hsfl.springbreak.backend.service.UserService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Description
import javax.transaction.Transactional


@Transactional
@SpringBootTest
class RatingTest {
    @Autowired
    private lateinit var controllerRecipe: RecipeController

    @Autowired
    private lateinit var controllerRating: RatingController

    @Autowired
    private lateinit var serviceUser: UserService

    @Autowired
    private lateinit var controllerUser: UserController



    private var recipeId: Long = 0
    private var userId: Long = 0

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
                                    ingredientName = "Milch",
                                    unit = "liter",
                                    amount = 400
                            )
                    )
            )
    val user = User.Register(
            firstName = "James",
            lastName = "Sullivan",
            email = "james.sullivan@moster-inc.com",
            password = "geheim"
    )


    @Description("Add Recipe and User to database")
    @BeforeEach
    fun saveData() {
        recipeId = controllerRecipe.createRecipe(recipe).data?.id ?: 0;
        userId = controllerUser.register(user).data?.id ?: 0;
    }



    @Test
    @Description("Test When recipeId and userId exist, then the user can rate the recipe once.")
    fun setRating() {
        var rating = Rating.SendRating(
              stars =1.5,
              recipeId=recipeId,
              userId=userId
        )
        val apiResponse = controllerRating.setRating(rating)
        assertTrue(apiResponse.success)
        assertEquals(1.5,apiResponse.data)
        rating= Rating.SendRating(
                stars =1.5,
                recipeId=recipeId+20,
                userId=userId
        )
        assertFalse(controllerRating.setRating(rating).success)
        rating= Rating.SendRating(
                stars =1.5,
                recipeId=recipeId,
                userId=userId+20
        )
        assertFalse(controllerRating.setRating(rating).success)


    }



}