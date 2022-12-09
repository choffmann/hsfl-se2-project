package com.hsfl.springbreak.backend.jpa

import com.hsfl.springbreak.backend.controller.RecipeController
import com.hsfl.springbreak.backend.entity.*
import org.junit.jupiter.api.Assertions.*
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
    private var testRecipe = RecipeEntity(
        id = null, //TODO: Id wird nicht automatisch vergeben
        title = "Schockomuffins",
        shortDescription = "Diese Muffins lieben alle! So schnell und einfach vorzubereiten und unglaublich saftig – das ist unser beliebtes Schokomuffin-Rezept.",
        price = 5.00,
        duration = 10.00,
        category = CategoryEntity(
            id = 999, //TODO: Id wird nicht automatisch vergeben
            name = "Backen"
        ),
        creator = UserEntity(
            id = 1, //TODO: Id wird nicht automatisch vergeben
            firstName = "Root",
            lastName = "Administrator",
            email = "root@admin.com",
            password = "geheim"
        ),
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
        val apiResponse = controller.createNewRecipe(testRecipe.toDto())
        assertEquals(true, apiResponse.success)

        if (apiResponse.success) {
            id = apiResponse.data!!.id
        }

        println("Recipe saved to id: $id")
    }

    @Test
    fun testGetRecipe() {
        // Test correct GET
        var apiResponse = controller.findRecipeById(id)
        assertEquals(true, apiResponse.success)

        // Test faulty GET
        apiResponse = controller.findRecipeById(1234)
        assertEquals(false, apiResponse.success)
    }

    /*
    @Test
    fun testPutRecipe() {
        // change id
        // test GET
    }

     */

    /*
    @Test
    fun testDeleteRecipe() {
        // Test correct DELETE
        var apiResponse = controller.findRecipeById(3)
        assertEquals(true, apiResponse.success)

        controller.deleteRecipe(0)
        apiResponse = controller.findRecipeById(3)
        assertEquals(false, apiResponse.success)

        // Test faulty DELETE
        controller.deleteRecipe(10)

        // TODO: Delete-ApiResponse has no success-field

    }

     */
}