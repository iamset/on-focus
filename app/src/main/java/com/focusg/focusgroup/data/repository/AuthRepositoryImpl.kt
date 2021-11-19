package com.focusg.focusgroup.data.repository

import android.util.Log
import com.focusg.focusgroup.common.Resource
import com.focusg.focusgroup.data.remote.dto.UserDto
import com.focusg.focusgroup.domain.model.User
import com.focusg.focusgroup.domain.service.AuthService
import com.focusg.focusgroup.domain.repository.AuthRepository
import com.focusg.focusgroup.domain.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    ): AuthRepository {
    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        callback: (data: Resource<Boolean>) -> Unit
    ) {
        withContext(Dispatchers.IO){
            authService.signUpWithEmailAndPassword(email, password){
                callback(it)
            }
        }

    }


    override suspend fun signInWithEmailAndPassword(email: String, password: String,callback: (data: Resource<Boolean>) -> Unit) {
        withContext(Dispatchers.IO){
            authService.signInWithEmailAndPassword(email, password){
                callback(it)
            }
        }
    }

    override suspend fun emailExists(email: String, callback: (data: Boolean) -> Unit) {
        withContext(Dispatchers.IO){
            authService.emailExists(email){
                callback(it)
            }
        }

    }
}