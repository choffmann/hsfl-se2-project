package com.hsfl.springbreak.backend.jpa

import com.hsfl.springbreak.backend.entity.UserEntity
import com.hsfl.springbreak.backend.model.User
import com.hsfl.springbreak.backend.repository.UserRepository
import com.hsfl.springbreak.backend.service.UserService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Description
import java.io.File
import java.nio.file.Paths
import javax.transaction.Transactional


@Transactional
@SpringBootTest
class UserJpaTest {
    @Autowired
    private lateinit var repository: UserRepository

    @Autowired
    private lateinit var service: UserService

    val demoUsers = listOf(
            UserEntity(
                    firstName = "James",
                    lastName = "Sullivan",
                    email = "james.sullivan@moster-inc.com",
                    password = "geheim"
            ),
            UserEntity(
                    firstName = "Mike",
                    lastName = "Glotzkowski",
                    email = "mike.glotzkowski@moster-inc.com",
                    password = "geheim"
            ),
            UserEntity(
                    firstName = "Randall",
                    lastName = "Boggs",
                    email = "randall.boggs@moster-inc.com",
                    password = "geheim"
            ),
            UserEntity(
                    firstName = "Henry",
                    lastName = "Waternoose",
                    email = "henry.waternoose@moster-inc.com",
                    password = "geheim"
            ),
            UserEntity(
                    firstName = "George",
                    lastName = "Sanderson",
                    email = "george.sanderson@moster-inc.com",
                    password = "geheim"
            ),
            UserEntity(
                    firstName = "Thaddeus",
                    lastName = "Bile",
                    email = "thaddeus.bile@moster-inc.com",
                    password = "geheim"
            )
    )
    @Description("Add users in database")
    @BeforeEach
    fun `fill db with demo data`() {
        repository.saveAll(demoUsers)
    }

//    @Test
//    fun `find all users`() {
//        val users = repository.findAll()
//        assertIterableEquals(users, demoUsers)
//    }

    @Test
    fun `find user by mail`() {
        val user = repository.findByEmail("james.sullivan@moster-inc.com")
        assertEquals(demoUsers.find { it.email == "james.sullivan@moster-inc.com" }, user)
    }

    @Test
    fun `find user by mail that does not exist`() {
        val user = repository.findByEmail("mail@not-exist.com")
        assertNull(user)
    }

    @Test
    @Description("Test register a person")
    fun testRegister() {
        val user = User.Register(
                firstName = "Max", lastName = "Mustermann", email = "max.mustermann@moster-inc.com",
                password = "hallo"
        )
        val apiResponse = service.registerUser(user)
        assertTrue(apiResponse.success)
    }
    @Test
    @Description("Test login a user")
    fun testLogin() {
        val user1 = User.Login(
                email = "thaddeus.bile@moster-inc.com", password = "geheim"
        )
        val apiResponse1 = service.loginUser(user1)
        assertTrue(apiResponse1.success)
    }

    @Test
    @Description("Test when the user enters incorrect login data")
    fun testLoginFailed() {
        val user = User.Login(
                email = "thaddeus.bile@moster-inc.com", password = "ma000"
        )
        val apiResponse = service.loginUser(user)
        assertFalse(apiResponse.success)
    }

    @Test
    @Description("Test when the user edits his personal data")
    fun testchangeProfile() {
        val user1 = User.ChangeProfile(
               id= 1,  firstName = "Hermin", lastName = "Mosa", password = ""
        )
        val apiResponse = service.changeProfile(user1)
        assertTrue(apiResponse.success)
    }
    @Test
    fun testChangeProfileFailed() {
        val user1 = User.ChangeProfile(
                id= 100,  firstName = "Hermin", lastName = "Mosa", password = ""
        )
        val apiResponse = service.changeProfile(user1)
        assertFalse(apiResponse.success)
    }
    @Test
    @Description("Test when the user adds a recipe to his favorite list")
    fun testSetFavoriteById() {
        val userId : Long= demoUsers[1].id!!
        val recipeId: Long = 2
        val apiResponse = service.setFavoriteById(recipeId, userId)
        assertTrue(apiResponse.success)
    }

    @Test
    fun testSetFavoriteByIdFailed() {
        val userId : Long= demoUsers[5].id!!
        val recipeId: Long = 20
        val apiResponse = service.setFavoriteById(recipeId, userId)
        assertFalse(apiResponse.success)
    }
    @Test
    @Description("Test when the user removes a recipe to his favorite list")
    fun deleteFavoriteById() {
        val userId : Long= demoUsers[1].id!!
        val recipeId: Long = 2
        val apiResponse = service.setFavoriteById(recipeId, userId)

        val apiResponse1 = service.deleteFavoriteById(recipeId,userId)
        assertTrue(apiResponse1.success)
    }

    @Test
    fun deleteFavoriteByIdFailed() {
        val userId : Long= demoUsers[4].id!!
        val recipeId: Long = 2

        val apiResponse1 = service.deleteFavoriteById(recipeId,userId)
        assertFalse(apiResponse1.success)
    }
}

