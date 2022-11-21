package com.hsfl.springbreak.backend.demo

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class DemoData(
    @Id @GeneratedValue val id: Long? = -1,
    @Column val firstName: String,
    @Column(nullable = false) val lastName: String
)
