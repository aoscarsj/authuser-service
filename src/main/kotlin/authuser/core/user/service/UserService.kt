package authuser.core.user.service

import authuser.core.user.data.User
import authuser.core.user.data.UpdateUserRequest
import java.util.*

interface UserService {

    fun findAll(): List<User>
    fun findById(userId: UUID): User?
    fun delete(userId: UUID)
    fun signup(user: User): User
    fun existsByUsername(user: User): Boolean
    fun update(userId: UUID, updateRequest: UpdateUserRequest): User
    fun updatePassword(userId: UUID, updateRequest: UpdateUserRequest)
}