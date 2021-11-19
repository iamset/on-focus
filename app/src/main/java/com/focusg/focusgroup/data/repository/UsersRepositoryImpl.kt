package com.focusg.focusgroup.data.repository

import com.focusg.focusgroup.common.Resource
import com.focusg.focusgroup.domain.model.User
import com.focusg.focusgroup.domain.repository.UsersRepository
import com.focusg.focusgroup.domain.service.CacheService
import com.focusg.focusgroup.domain.service.UserService
import com.focusg.focusgroup.firebase.FirebaseUsersService

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val cacheService: CacheService
    ) : UsersRepository {
    override suspend fun usernameExists(
        username: String,
        callback: (data: Resource<Boolean>) -> Unit
    ) {
        withContext(Dispatchers.IO){
            userService.usernameExists(username){ callback(it) }
        }
    }


    override suspend fun cacheCurrentUser(user: User) {
        cacheService.cacheCurrentUser(user)
    }

    override suspend fun getUserDocument(username: String, callback: (data: Resource<User>) -> Unit) {
        withContext(Dispatchers.IO){
            userService.getUserDocument(username){ callback(it) }
        }
    }

    override suspend fun saveUser(user: User, callback: (data: Resource<Boolean>) -> Unit) {
        withContext(Dispatchers.IO){
            userService.saveUser(user){ callback(it) }
        }

    }

    override suspend fun getCurrentUserFromCache(): User{
        return cacheService.getCurrentUserFromCache()
    }

    override suspend fun saveUsername(
        username: String,
        email:String,
        callback: (data: Resource<Boolean>) -> Unit,
    ) {
        withContext(Dispatchers.IO){
            userService.saveUsername(username, email){ callback(it) }
        }

    }

    override suspend fun deleteUser(username: String, callback: (data: Resource<Boolean>) -> Unit) {
        withContext(Dispatchers.IO){
            userService.deleteUser(username){ callback(it) }
        }
    }

    override suspend fun getUsernameDocumentId(
        email: String,
        callback: (data: Resource<String>) -> Unit
    ) {
        withContext(Dispatchers.IO){
            userService.getUsernameDocumentId(email){callback(it)}
        }
    }


}