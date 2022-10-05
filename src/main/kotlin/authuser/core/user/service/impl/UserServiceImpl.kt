package authuser.core.user.service.impl

import authuser.core.user.repository.UserRepository
import authuser.core.user.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService