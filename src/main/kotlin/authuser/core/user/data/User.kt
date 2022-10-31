package authuser.core.user.data

import authuser.common.extension.isCPF
import authuser.common.extension.isEmail
import authuser.common.rest.RestItemError
import authuser.core.user.exception.RegistrationUserException
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var userId: UUID? = null,
    @Column(unique = true, length = 50, nullable = false)
    val username: String = "",
    @Column(unique = true, length = 50, nullable = false)
    var email: String = "",
    @Column(nullable = false)
    @JsonIgnore
    var password: String = "",
    @Column(length = 150, nullable = false)
    var fullName: String = "",
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val userStatus: UserStatus = UserStatus.ACTIVE,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val userType: UserType = UserType.STUDENT,
    @Column(length = 20)
    var phoneNumber: String = "",
    @Column(length = 20)
    var cpf: String = "",
    var imageUrl: String = "",
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    val created: LocalDateTime = LocalDateTime.now(ZoneId.of("UTC")),
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    val updated: LocalDateTime? = null
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L

        fun from(request: CreateUserRequest): User {

            validRequest(request)
            request.apply {

                return User(
                    username = username,
                    password = password,
                    cpf = cpf,
                    phoneNumber = phoneNumber,
                    fullName = fullName,
                    email = email
                )
            }
        }

        private fun validRequest(request: CreateUserRequest) {

            val errors: MutableList<RestItemError> = mutableListOf()
            val errorCode = "REGISTRATION_USER"

            val usernameSizeInvalidMessage = "Username must be between 4 and 30 characters"
            val usernameContainsSpaceMessage = "Username cannot have a space"
            val emailInvalidMessage = "The email is not valid"
            val passwordSizeInvalidMessage = "Password must be between 8 and 25 characters"
            val cpfInvalidMessage = "The CPF is not valid"

            request.apply {

                if (username.length < 4 || username.length > 30)
                    errors.add(RestItemError(usernameSizeInvalidMessage, "${errorCode}_001"))
                if (username.contains(" "))
                    errors.add(RestItemError(usernameContainsSpaceMessage, "${errorCode}_002"))
                if (email.isEmail().not())
                    errors.add(RestItemError(emailInvalidMessage, "${errorCode}_003"))
                if (password.length < 8 || password.length > 25)
                    RestItemError(passwordSizeInvalidMessage, "${errorCode}_004")
                if (cpf.isCPF().not())
                    RestItemError(cpfInvalidMessage, "${errorCode}_005")
            }

            if(errors.isNotEmpty())
                throw RegistrationUserException("The request is not valid", HttpStatus.CONFLICT, errors)
        }
    }
}

enum class UserStatus { ACTIVE, BLOCKED }
enum class UserType { ADMIN, STUDENT, INSTRUCTOR }