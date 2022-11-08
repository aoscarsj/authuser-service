package authuser.core.user.service

import authuser.core.user.data.CreateUserRequest
import authuser.core.user.data.UpdateUserRequest
import authuser.core.user.data.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface UserService {

    fun findAll(page: Pageable): Page<User>
    fun findById(userId: UUID): User?
    fun delete(userId: UUID)
    fun signup(request: CreateUserRequest): User
    fun existsByUsername(user: User): Boolean
    fun update(userId: UUID, updateRequest: UpdateUserRequest): User
    fun updatePassword(userId: UUID, updateRequest: UpdateUserRequest)
    fun updateImage(userId: UUID, updateRequest: UpdateUserRequest): User
}