package authuser.core.user.data

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonView
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CreateUserRequest(

    @NotBlank
    @Size(min = 4, max = 30)
    @JsonView(UserView.Companion.RegistrationPost::class)
    val username: String,

    @NotBlank
    @Email
    @JsonView(UserView.Companion.RegistrationPost::class)
    val email: String,

    @NotBlank
    @Size(min = 8, max = 20)
    @JsonView(UserView.Companion.RegistrationPost::class)
    val password: String,

    @JsonView(UserView.Companion.RegistrationPost::class)
    val fullName: String,

    @JsonView(UserView.Companion.RegistrationPost::class)
    val phoneNumber: String,

    @NotBlank
    @CPF
    @JsonView(UserView.Companion.RegistrationPost::class)
    val cpf: String,
) {

    interface UserView {
        companion object {
            interface RegistrationPost
        }
    }
}