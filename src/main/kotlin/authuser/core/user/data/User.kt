package authuser.core.user.data

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var userId: UUID,
    @Column(unique = true, length = 50, nullable = false)
    private val username: String,
    @Column(unique = true, length = 50, nullable = false)
    private val email: String,
    @Column(nullable = false)
    @JsonIgnore
    private val password: String,
    @Column(length = 150, nullable = false)
    private val fullName: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private val userStatus: UserStatus,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private val userType: UserType,
    @Column(length = 20)
    private val phoneNumber: String,
    @Column(length = 20)
    private val cpf: String,
    private val imageUrl: String,
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private val created: LocalDateTime = LocalDateTime.now(),
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private val updated: LocalDateTime? = null
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}

enum class UserStatus { ACTIVE, BLOCKED }
enum class UserType { ADMIN, STUDENT, INSTRUCTOR }