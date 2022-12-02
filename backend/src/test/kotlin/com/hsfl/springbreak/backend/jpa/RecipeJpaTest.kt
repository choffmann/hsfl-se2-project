package com.hsfl.springbreak.backend.jpa

import com.hsfl.springbreak.backend.controller.RecipeController
import com.hsfl.springbreak.backend.controller.RecipeService
import com.hsfl.springbreak.backend.entity.*
import com.hsfl.springbreak.backend.model.Rating
import com.hsfl.springbreak.backend.repository.RecipeRepository
import com.hsfl.springbreak.backend.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.util.Assert
import java.time.LocalDate
import javax.transaction.Transactional

@Transactional
@SpringBootTest
class RecipeJpaTest {

    @Autowired
    private lateinit var service: RecipeService
    private lateinit var controller: RecipeController

    private var testRecipe = RecipeEntity(
        title = "Schockomuffins",
        shortDescription = "Diese Muffins lieben alle! So schnell und einfach vorzubereiten und unglaublich saftig – das ist unser beliebtes Schokomuffin-Rezept.",
        price = 5.00,
        duration = 10.00,
        rating = null,
        difficulty = DifficultyEntity(
            name = "Leicht"
        ),
        category = CategoryEntity(
            name = "Backen"
        ),
        creator = UserEntity(
            firstName = "Jonathan",
            lastName = "Duval",
            email = "jonathan.duval@monster-inc.com",
            password = "secret"
        ),
        createTime = LocalDate.now(),
        ingredients = null,
        image = "https://www.einfachbacken.de/sites/einfachbacken.de/files/styles/700_530/public/2019-05/schokomuffins.jpg?h=a1e1a043&itok=by_zr_C7",
        longDescription = "Butter mit Zucker und Vanillezucker verrühren. Eier unterrühren. Zartbitterschokolade grob hacken. Ofen auf 180 Grad (Umluft: 160 Grad) vorheizen. Mehl mit Kakaopulver, Salz und Backpulver vermischen. Mehlmischung mit der Milch zur Butter-Zuckermischung geben und alles gut verrühren. Etwa zwei Drittel der gehackten Schokolade unterheben",
        views = 0
    )

    /*
    @Before
    fun clearEntries() {
        // Clear all entries
    }
     */

    @Test
    fun testPostRecipe() {
        // Test correct POST
        val apiResponse = controller.createRecipe(testRecipe.toDto())
        assertEquals(apiResponse?.success, true)
    }

    @Test
    fun testGetRecipe() {
        // Test correct GET
        var apiResponse = controller.getRecipeById(0)
        assertEquals(apiResponse.success, true)

        // Test faulty GET
        apiResponse = controller.getRecipeById(10)
        assertEquals(apiResponse.success, false)
    }

    @Test
    fun testPutRecipe() {
        // change id
        // test GET
    }

    @Test
    fun testDeleteRecipe() {
        // Test correct DELETE
        var apiResponse = controller.getRecipeById(0)
        assertEquals(apiResponse.success, true)

        controller.deleteRecipe(0)
        apiResponse = controller.getRecipeById(0)
        assertEquals(apiResponse.success, false)

        // Test faulty DELETE
        controller.deleteRecipe(10)

        // TODO: Delete-ApiResponse has no success-field

    }
}