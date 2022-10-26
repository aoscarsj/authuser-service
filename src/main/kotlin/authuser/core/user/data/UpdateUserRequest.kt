package authuser.core.user.data

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonView
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UpdateUserRequest(

    @Email
    @JsonView(UserView.Companion.UserPut::class)
    val email: String? = null,

    @NotBlank(groups = [UserView.Companion.PasswordPut::class])
    @Size(min = 8, max = 20)
    @JsonView(UserView.Companion.PasswordPut::class)
    val password: String? = null,

    @NotBlank(groups = [UserView.Companion.PasswordPut::class])
    @Size(min = 8, max = 20)
    @JsonView(UserView.Companion.PasswordPut::class)
    val oldPassword: String? = null,

    @JsonView(UserView.Companion.UserPut::class)
    val fullName: String? = null,

    @JsonView(UserView.Companion.UserPut::class)
    val phoneNumber: String? = null,

    @CPF
    @JsonView(UserView.Companion.UserPut::class)
    val cpf: String? = null,

    @NotBlank(groups = [UserView.Companion.ImagePut::class])
    @JsonView(UserView.Companion.ImagePut::class)
    val imageUrl: String? = null
) {

    interface UserView {
        companion object {
            interface UserPut
            interface PasswordPut
            interface ImagePut
        }
    }
}