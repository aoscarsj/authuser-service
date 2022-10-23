package authuser.common.rest

data class RestResponse<T>(
    val message: String,
    val response: T? = null,
    val success: Boolean = true,
    val httpCode: Int = 200,
    val errors: Collection<RestItemError> = emptyList()
)
