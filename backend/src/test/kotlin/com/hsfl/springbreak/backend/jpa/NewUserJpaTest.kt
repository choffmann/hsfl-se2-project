package com.hsfl.springbreak.backend.jpa


import com.hsfl.springbreak.backend.EntityConfiguration
import org.assertj.core.api.JUnitSoftAssertions
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [EntityConfiguration::class])
class NewUserJpaTest() {

    @get:Rule
    var softly = JUnitSoftAssertions()

    @Test
    fun `'loginUser' should return an Error if the mail doesn't exists`() {

    }
}