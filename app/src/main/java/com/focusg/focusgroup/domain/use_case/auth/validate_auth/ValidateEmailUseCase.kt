package com.focusg.focusgroup.domain.use_case.auth.validate_auth

import com.focusg.focusgroup.domain.util.AuthValidation
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(private val authValidator: AuthValidation) {
    operator fun invoke(email: String): Boolean{
        return authValidator.isValidEmail(email)

    }
}