package authuser.core.user.service.impl

import authuser.common.extension.isCPF
import authuser.common.extension.isEmail
import authuser.common.rest.RestException
import authuser.common.rest.RestItemError
import authuser.core.user.data.User
import authuser.core.user.data.UserCreateRequest
import authuser.core.user.data.UserSearchRequest
import authuser.core.user.data.UserUpdateRequest
import authuser.core.user.exception.PasswordException
import authuser.core.user.exception.RegistrationUserException
import authuser.core.user.exception.UserException
import authuser.core.user.repository.UserRepository
import authuser.core.user.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    override fun findAll(searchRequest: UserSearchRequest, page: Pageable): Page<User> {

        searchRequest.apply {

            if (email != null && status != null && type != null)
                return userRepository.findAllByTypeAndStatusAndEmailContains(
                    type, status, email, page
                )
            if (status != null && type != null)
                return userRepository.findAllByTypeAndStatus(type, status, page)
            if (status != null && email != null)
                return userRepository.findAllByStatusAndEmailContains(status, email, page)
            if (type != null && email != null)
                return userRepository.findAllByTypeAndEmailContains(type, email, page)
            if (status != null)
                return userRepository.findAllByStatus(status, page)
            if (type != null)
                return userRepository.findAllByType(type, page)
            if (email != null)
                return userRepository.findAllByEmailContains(email, page)
        }

        return userRepository.findAll(page)
    }


    override fun findById(userId: UUID): User {

        return userRepository.findByIdOrNull(userId) ?: throw UserException(
            "User not found",
            NOT_FOUND
        )
    }

    override fun delete(userId: UUID) = userRepository.delete(findById(userId))

    override fun existsByUsername(user: User): Boolean =
        userRepository.existsByUsername(user.username)

    override fun update(userId: UUID, updateRequest: UserUpdateRequest): User {

        validateRequest(updateRequest)

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

    private fun validateRequest(request: UserUpdateRequest) {

        val errors: MutableList<RestItemError> = mutableListOf()
        val errorCode = "UPDATE_USER"

        request.apply {

            email?.let {

                if (!email.isEmail())
                    errors.add(RestItemError("Email is not valid", code = "${errorCode}_E01"))
                if (userRepository.existsByEmail(email))
                    errors.add(RestItemError("email already registered", code = "${errorCode}_E02"))
            }

            cpf?.let {
                if (!cpf.isCPF())
                    errors.add(RestItemError("CPF is not valid", code = "${errorCode}_C01"))
                if (userRepository.existsByCpf(cpf))
                    errors.add(RestItemError("cpf already registered", code = "${errorCode}_E02"))
            }
        }

        if (errors.isNotEmpty())
            throw RegistrationUserException("The request is not valid", CONFLICT, errors)
    }

    override fun updatePassword(userId: UUID, updateRequest: UserUpdateRequest) {

        val user = findById(userId)
        validatePassword(user, updateRequest)

        user.password = passwordEncoder.encode(updateRequest.password)
        userRepository.save(user)
    }

    private fun validatePassword(user: User, updateRequest: UserUpdateRequest) {

        updateRequest.apply {
            if (oldPassword.isNullOrEmpty() || password.isNullOrEmpty())
                throw PasswordException("Password cannot be null or empty.", BAD_REQUEST)
            if (password.length < 8 || password.length > 25)
                throw PasswordException("Password must be between 8 and 25 digits.", BAD_REQUEST)
            if (passwordEncoder.matches(oldPassword, user.password).not())
                throw PasswordException("Error: Mismatched old password.", CONFLICT)
        }
    }

    override fun updateImage(userId: UUID, updateRequest: UserUpdateRequest): User {

        val user = findById(userId)

        if (updateRequest.imageUrl.isNullOrEmpty())
            throw UserException("ImageUrl cannot be null or empty", BAD_REQUEST)

        user.imageUrl = updateRequest.imageUrl
        userRepository.save(user)

        return user
    }


    override fun signup(request: UserCreateRequest): User {

        val user = User.from(request)
        validate(user)

        user.password = passwordEncoder.encode(user.password)
        userRepository.save(user)

        return user
    }


    private fun validate(user: User): Boolean {

        if (existsByUsername(user))
            throw RestException(
                message = "Error: Username is Already taken!",
                httpStatus =
                CONFLICT,
            )
        if (userRepository.existsByEmail(user.email))
            throw RestException(message = "Error: Email is Already taken!", httpStatus = CONFLICT)

        return true
    }
}
