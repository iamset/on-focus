package com.focusg.focusgroup.domain.use_case.auth.signin

import com.focusg.focusgroup.common.Resource
import com.focusg.focusgroup.domain.model.User
import com.focusg.focusgroup.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(email:String, password:String, callback: (data: Resource<Boolean>) -> Unit){
        authRepository.signInWithEmailAndPassword(email, password){
            callback(it)
        }
    }
}