package com.focusg.focusgroup.domain.use_case.auth.validate_auth

import com.focusg.focusgroup.domain.validation.AuthValidation
import javax.inject.Inject

class ValidateUsernameUseCase @Inject constructor(private val authValidator: AuthValidation) {
    operator fun invoke(username: String): Boolean{
        return authValidator.isValidUsername(username)

    }
}