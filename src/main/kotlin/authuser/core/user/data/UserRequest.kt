package authuser.core.user.data

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonView

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserRequest(
    @JsonView(UserView.Companion.RegistrationPost::class)
    val username: String,
    @JsonView(UserView.Companion.RegistrationPost::class)
    val email: String,
    @JsonView(UserView.Companion.RegistrationPost::class, UserView.Companion.PasswordPut::class)
    val password: String,
    @JsonView(UserView.Companion.PasswordPut::class)
    val oldPassword: String? = null,
    @JsonView(UserView.Companion.RegistrationPost::class, UserView.Companion.UserPut::class)
    val fullName: String,
    @JsonView(UserView.Companion.RegistrationPost::class, UserView.Companion.UserPut::class)
    val phoneNumber: String,
    @JsonView(UserView.Companion.RegistrationPost::class, UserView.Companion.UserPut::class)
    val cpf: String,
    @JsonView(UserView.Companion.ImagePut::class)
    val imageUrl: String = ""
) {

    public interface UserView {
        companion object {
            public interface RegistrationPost {}
            public interface UserPut {}
            public interface PasswordPut {}
            public interface ImagePut {}
        }
    }
}