package com.focusg.focusgroup.domain.use_case.auth.signup

import com.focusg.focusgroup.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EmailExistsUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, callback: (data: Boolean) -> Unit){
        withContext(Dispatchers.IO){
            authRepository.emailExists(email){
                callback(it)
            }
        }
    }
}