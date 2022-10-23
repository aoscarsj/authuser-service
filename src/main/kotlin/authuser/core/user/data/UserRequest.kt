package authuser.core.user.data

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserRequest(
    val username: String,
    val email: String,
    val password: String,
    val oldPassword: String? = null,
    val fullName: String,
    val phoneNumber: String,
    val cpf: String,
    val imageUrl: String
)