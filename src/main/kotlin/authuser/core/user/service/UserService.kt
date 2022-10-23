package authuser.core.user.service

import authuser.core.user.data.User
import java.util.*

interface UserService {

    fun findAll(): List<User>
    fun findById(userId: UUID): User?
    fun delete(user: User)
    fun signup(user: User): User
    fun existsByUsername(user: User): Boolean
}