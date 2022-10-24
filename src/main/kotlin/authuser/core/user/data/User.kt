package authuser.core.user.data

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
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
    val email: String = "",
    @Column(nullable = false)
    @JsonIgnore
    var password: String = "",
    @Column(length = 150, nullable = false)
    val fullName: String = "",
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val userStatus: UserStatus = UserStatus.ACTIVE,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val userType: UserType = UserType.STUDENT,
    @Column(length = 20)
    val phoneNumber: String = "",
    @Column(length = 20)
    val cpf: String = "",
    val imageUrl: String = "",
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    val created: LocalDateTime = LocalDateTime.now(ZoneId.of("UTC")),
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    val updated: LocalDateTime? = null
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L

        fun from(userRequest: UserRequest): User{

            val user = User() // TODO implement this method

            return user
        }
    }
}

enum class UserStatus { ACTIVE, BLOCKED }
enum class UserType { ADMIN, STUDENT, INSTRUCTOR }