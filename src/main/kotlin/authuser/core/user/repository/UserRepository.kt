package authuser.core.user.repository

import authuser.core.user.data.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {

    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun existsByCpf(cpf: String): Boolean
}