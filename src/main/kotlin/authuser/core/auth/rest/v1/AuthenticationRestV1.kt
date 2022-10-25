package authuser.core.auth.rest.v1

import authuser.common.rest.RestResponse
import authuser.core.user.data.CreateUserRequest
import authuser.core.user.data.User
import authuser.core.user.service.UserService
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/auth")
class AuthenticationRestV1(
    val userService: UserService,
) {

    @PostMapping("/signup")
    fun registerUser(
        @RequestBody @JsonView(CreateUserRequest.UserView.Companion.RegistrationPost::class)
        userRequest: CreateUserRequest
    ): RestResponse<User> {
        val user = User.from(userRequest)
        val createdUser = userService.signup(user)
        return RestResponse(
            message = "User was successfully created", response = createdUser,
            httpStatus = HttpStatus.CREATED
        )
    }
}