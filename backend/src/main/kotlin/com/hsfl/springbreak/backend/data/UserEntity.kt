package com.hsfl.springbreak.backend.data

import com.hsfl.springbreak.common.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "users")
data class UserEntity(
    @Id @GeneratedValue val id: Long? = null,
    @Column val firstName: String,
    @Column val lastName: String,
    @Column val email: String,
    @Column val password: String
) : DataEntity<User, UserEntity> {

    override fun toDto(): User = User(
        id = this.id!!,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        password = this.password
    )

    override fun fromDto(dto: User): UserEntity = UserEntity(
        id = dto.id,
        firstName = dto.firstName,
        lastName = dto.lastName,
        email = dto.email,
        password = dto.password
    )
}
