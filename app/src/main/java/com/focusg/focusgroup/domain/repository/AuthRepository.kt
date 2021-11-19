package com.focusg.focusgroup.domain.repository

import com.focusg.focusgroup.common.Resource
import com.focusg.focusgroup.data.remote.dto.UserDto
import com.focusg.focusgroup.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        callback: (data: Resource<Boolean>) -> Unit
    )
    suspend fun signInWithEmailAndPassword(email: String, password: String,callback: (data: Resource<Boolean>) -> Unit )
    suspend fun emailExists(email: String, callback: (data: Boolean) -> Unit)
}