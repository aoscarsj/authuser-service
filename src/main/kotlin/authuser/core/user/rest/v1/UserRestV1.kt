package authuser.core.user.rest.v1

import authuser.common.rest.RestResponse
import authuser.core.user.data.User
import authuser.core.user.data.UpdateUserRequest
import authuser.core.user.service.UserService
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/users")
class UserRestV1(
    val userService: UserService
) {

    @GetMapping
    fun findAll(): RestResponse<List<User>> = RestResponse(
        "Users was collected", userService
            .findAll(), httpCode = OK.value()
    )

    @GetMapping("/{userId}")
    fun find(@PathVariable(value = "userId") userId: UUID): RestResponse<User?> {

        val user = userService.findById(userId)

        return RestResponse(
            message = "user was collected",
            response = user,
            httpCode = OK.value()
        )
    }

    @DeleteMapping("/{userId}")
    fun remove(@PathVariable(value = "userId") userId: UUID): RestResponse<Any> {

        userService.delete(userId)

        return RestResponse("User deleted successful")
    }

    @PutMapping("/{userId}")
    fun update(
        @PathVariable(value = "userId") userId: UUID, @RequestBody @JsonView(
            UpdateUserRequest.UserView.Companion.UserPut::class
        ) request: UpdateUserRequest
    ): RestResponse<Any> {

        val user = userService.update(userId, request)

        return RestResponse("User was updated successful", response = user)
    }

    @PutMapping("/{userId}/password")
    fun updatePassword(
        @PathVariable(value = "userId") userId: UUID, @RequestBody @JsonView(
            UpdateUserRequest.UserView.Companion.PasswordPut::class
        ) request: UpdateUserRequest
    ): RestResponse<Any> {

        userService.updatePassword(userId, request)

        return RestResponse("Password updated successfully.")
    }

    @PutMapping("/{userId}/image")
    fun updateImage(
        @PathVariable(value = "userId") userId: UUID, @RequestBody @JsonView(
            UpdateUserRequest.UserView.Companion.ImagePut::class
        ) request: UpdateUserRequest
    ): RestResponse<Any> {

        val user = userService.updateImage(userId, request)

        return RestResponse("Image updated successfully.", response = user)
    }
}