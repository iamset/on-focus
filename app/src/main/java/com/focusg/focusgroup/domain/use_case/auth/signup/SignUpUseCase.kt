package com.focusg.focusgroup.domain.use_case.auth.signup

import com.focusg.focusgroup.common.Resource
import com.focusg.focusgroup.data.remote.dto.UserDto
import com.focusg.focusgroup.domain.model.User
import com.focusg.focusgroup.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        email:String,
        password: String,
        callback: (data: Resource<Boolean>) -> Unit
    ){
         repository.signUpWithEmailAndPassword(email, password){
            callback(it)
        }
    }

}
