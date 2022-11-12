package com.hsfl.springbreak.spring_break.demo

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class DemoData(
    @Id @GeneratedValue val id: Long? = -1,
    @Column(nullable = false) val firstName: String,
    @Column(nullable = false) val lastName: String
)
