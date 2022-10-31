package authuser.core.user.data

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonView
import javax.validation.constraints.Size

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UpdateUserRequest(

    @JsonView(UserView.Companion.UserPut::class)
    val email: String? = null,

    @JsonView(UserView.Companion.PasswordPut::class)
    @Size(min = 8, max = 20)
    val password: String? = null,


    @JsonView(UserView.Companion.PasswordPut::class)
    @Size(min = 8, max = 20)
    val oldPassword: String? = null,

    @JsonView(UserView.Companion.UserPut::class)
    val fullName: String? = null,

    @JsonView(UserView.Companion.UserPut::class)
    val phoneNumber: String? = null,

    @JsonView(UserView.Companion.UserPut::class)
    val cpf: String? = null,

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