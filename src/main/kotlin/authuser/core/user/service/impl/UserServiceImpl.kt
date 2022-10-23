package authuser.core.user.service.impl

import authuser.common.rest.RestException
import authuser.core.user.data.User
import authuser.core.user.repository.UserRepository
import authuser.core.user.service.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    private val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()

    override fun findAll(): List<User> = userRepository.findAll()

    override fun findById(userId: UUID): User? = userRepository.findByIdOrNull(userId)

    override fun delete(user: User) = userRepository.delete(user)

    override fun existsByUsername(user: User): Boolean =
        userRepository.existsByUsername(user.username)

    override fun signup(user: User): User {

        validate(user)

        user.password = passwordEncoder.encode(user.password)
        userRepository.save(user)

        return user
    }


    private fun validate(user: User): Boolean {

        if (existsByUsername(user))
            throw RestException(
                message = "Error: Username is Already taken!",
                httpStatus = CONFLICT,
            )
        if (userRepository.existsByEmail(user.email))
            throw RestException(message = "Error: Email is Already taken!", httpStatus = CONFLICT)

        return true
    }
}
