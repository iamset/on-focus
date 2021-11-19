package com.focusg.focusgroup.domain.repository


import com.focusg.focusgroup.common.Resource
import com.focusg.focusgroup.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun usernameExists(username: String, callback: (data: Resource<Boolean>) -> Unit)

    suspend fun cacheCurrentUser(user: User)

    suspend fun getUserDocument(username: String, callback: (data: Resource<User>) -> Unit)

    suspend fun saveUser(user: User, callback: (data: Resource<Boolean>) -> Unit)

    suspend fun getCurrentUserFromCache():User

    suspend fun saveUsername(username: String, email:String, callback: (data: Resource<Boolean>) -> Unit)

    suspend fun deleteUser(username:String, callback: (data: Resource<Boolean>) -> Unit)

    suspend fun getUsernameDocumentId(email:String, callback: (data: Resource<String>)->Unit)
}