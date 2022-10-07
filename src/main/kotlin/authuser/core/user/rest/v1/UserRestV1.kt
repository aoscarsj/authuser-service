package authuser.core.user.rest.v1

import authuser.core.user.data.User
import authuser.core.user.service.UserService
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/users")
class UserRestV1(
    val userService: UserService
) {

    @GetMapping
    fun findAll(): ResponseEntity<List<User>> {
        return ResponseEntity.status(OK).body(userService.findAll())
    }

    @GetMapping("/{userId}")
    fun find(@PathVariable(value = "userId")userId: UUID): ResponseEntity<User?> {

        val user = userService.findById(userId)

        if(user != null)
            return ResponseEntity.status(OK).body(user)

        return ResponseEntity.status(NOT_FOUND).body(user)
    }

    @DeleteMapping
    fun remove(@PathVariable(value = "userId") userId: UUID): ResponseEntity<Any> {

        val user = userService.findById(userId)
            ?: return ResponseEntity.status(NOT_FOUND).body("User not found")
        userService.delete(user)

        return ResponseEntity.status(OK).body("User deleted successful")
    }
}