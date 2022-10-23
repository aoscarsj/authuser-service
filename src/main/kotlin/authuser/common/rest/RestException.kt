package authuser.common.rest

import org.springframework.http.HttpStatus

open class RestException (
    val httpStatus: HttpStatus,
    val httpCode: Int = httpStatus.value(),
    override val message: String,
    val errors: Collection<RestItemError> = emptyList()
) : Exception(message)