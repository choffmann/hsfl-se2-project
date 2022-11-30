package com.hsfl.springbreak.backend.jpa

import com.hsfl.springbreak.backend.entity.UserEntity
import com.hsfl.springbreak.backend.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional


@Transactional
@SpringBootTest
class UserJpaTest {
    @Autowired
    private lateinit var repository: UserRepository

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

    @BeforeEach
    fun `fill db with demo data`() {
        repository.saveAll(demoUsers)
    }

    @Test
    fun `find all users`() {
        val users = repository.findAll()
        assertIterableEquals(users, demoUsers)
    }

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
}