package com.focusg.focusgroup.domain.service



import android.util.Log
import com.focusg.focusgroup.common.Resource
import com.focusg.focusgroup.data.remote.dto.UserDto
import com.focusg.focusgroup.domain.model.User
import com.focusg.focusgroup.firebase.FirebaseAuthService
import kotlinx.coroutines.flow.*

import javax.inject.Inject

class AuthService @Inject constructor(
    val auth: FirebaseAuthService
) {

    fun signInWithEmailAndPassword(email: String, password: String, callback: (data: Resource<Boolean>) -> Unit){
        auth.signInWithEmailAndPassword(email, password){
            callback(it)
        }
    }

    fun signUpWithEmailAndPassword(
        email:String,
        password:String,
        callback: (data: Resource<Boolean>) -> Unit
    ) {
        auth.signUpWithEmailAndPassword(email, password){
            callback(it)
        }

    }
    fun emailExists(email: String, callback: (data: Boolean) -> Unit){
        auth.emailExists(email){
            when(it){
                is Resource.Success -> {
                    val emailExists = it.data?: emptyList()
                    if(emailExists.isNotEmpty()) callback(true) else callback(false)
                }
                is Resource.Error -> {callback(false)}
            }
        }
    }
}


