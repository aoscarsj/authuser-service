package authuser.core.user.service.impl

import authuser.common.rest.RestException
import authuser.core.user.data.User
import authuser.core.user.data.UpdateUserRequest
import authuser.core.user.exception.PasswordException
import authuser.core.user.exception.UserException
import authuser.core.user.repository.UserRepository
import authuser.core.user.service.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    private val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()

    override fun findAll(): List<User> = userRepository.findAll()

    override fun findById(userId: UUID): User {

        return userRepository.findByIdOrNull(userId) ?: throw UserException(
            "User not found",
            NOT_FOUND
        )
    }

    override fun delete(userId: UUID) = userRepository.delete(findById(userId))

    override fun existsByUsername(user: User): Boolean =
        userRepository.existsByUsername(user.username)

    override fun update(userId: UUID, updateRequest: UpdateUserRequest): User {

        val user = findById(userId)
        updateRequest.apply {

            if (!email.isNullOrEmpty())
                user.email = email
            if (!fullName.isNullOrEmpty())
                user.fullName = fullName
            if (!phoneNumber.isNullOrEmpty())
                user.phoneNumber = phoneNumber
            if (!cpf.isNullOrEmpty())
                user.cpf = cpf
        }
        userRepository.save(user)

        return user
    }

    override fun updatePassword(userId: UUID, updateRequest: UpdateUserRequest) {

        val user = findById(userId)
        validatePassword(user, updateRequest)

        user.password = passwordEncoder.encode(updateRequest.password)
        userRepository.save(user)
    }

    override fun updateImage(userId: UUID, updateRequest: UpdateUserRequest): User {

        val user = findById(userId)

        if(updateRequest.imageUrl.isNullOrEmpty())
            throw UserException("ImageUrl cannot be null or empty", BAD_REQUEST)

        user.imageUrl = updateRequest.imageUrl
        userRepository.save(user)

        return user
    }

    private fun validatePassword(user: User, updateRequest: UpdateUserRequest) {

        updateRequest.apply {
            if(oldPassword.isNullOrEmpty() || password.isNullOrEmpty())
                throw PasswordException("Password cannot be null or empty.", BAD_REQUEST)
            if(password.length < 8)
                throw PasswordException("Password cannot be less than 8 digits.", BAD_REQUEST)
            if(passwordEncoder.matches(oldPassword, user.password).not())
                throw PasswordException("Error: Mismatched old password.", CONFLICT)
        }
    }


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
