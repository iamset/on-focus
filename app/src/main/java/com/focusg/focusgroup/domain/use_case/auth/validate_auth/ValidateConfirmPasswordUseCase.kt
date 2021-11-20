package com.focusg.focusgroup.domain.use_case.auth.validate_auth

import com.focusg.focusgroup.domain.validation.AuthValidation
import javax.inject.Inject

class ValidateConfirmPasswordUseCase @Inject constructor(private val authValidator: AuthValidation) {
    operator fun invoke(password1: String, password2:String): Boolean{
        return authValidator.isValidConfirmPassword(password1, password2)
    }
}