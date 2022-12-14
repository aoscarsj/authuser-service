package authuser.core.auth.rest.v1

import authuser.common.rest.RestResponse
import authuser.core.user.data.UserCreateRequest
import authuser.core.user.data.UserCreateRequest.UserView.Companion.RegistrationPost
import authuser.core.user.data.User
import authuser.core.user.service.UserService
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/auth")
class AuthenticationRestV1(
    private val userService: UserService,
) {

    @PostMapping("/signup")
    fun registerUser(
        @RequestBody @JsonView(RegistrationPost::class) userRequest: UserCreateRequest
    ): RestResponse<User> {

        val createdUser = userService.signup(userRequest)

        return RestResponse(
            message = "User was successfully created", response = createdUser,
            httpStatus = HttpStatus.CREATED
        )
    }
}