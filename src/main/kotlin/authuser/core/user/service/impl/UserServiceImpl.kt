package authuser.core.user.service.impl

import authuser.core.user.data.User
import authuser.core.user.repository.UserRepository
import authuser.core.user.service.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override fun findAll(): List<User> = userRepository.findAll()

    override fun findById(userId: UUID): User? = userRepository.findByIdOrNull(userId)

    override fun delete(user: User) = userRepository.delete(user)

}
