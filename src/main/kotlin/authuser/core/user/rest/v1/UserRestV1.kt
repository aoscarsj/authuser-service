package authuser.core.user.rest.v1

import authuser.common.rest.RestException
import authuser.common.rest.RestResponse
import authuser.core.user.data.User
import authuser.core.user.data.UserRequest
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

        return RestResponse("User deleted successful", httpCode = OK.value())
    }

    @PutMapping("/{userId}")
    fun update(
        @PathVariable(value = "userId") userId: UUID, @JsonView(
            UserRequest.UserView.Companion.UserPut::class
        ) request: UserRequest
    ): RestResponse<Any> {

        userService.update(userId, request)

        return RestResponse("User was updated successful")
    }
}