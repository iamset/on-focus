package com.focusg.focusgroup.domain.service

import com.focusg.focusgroup.common.Resource
import com.focusg.focusgroup.domain.model.User
import com.focusg.focusgroup.firebase.FirebaseUsersService
import javax.inject.Inject

class UserService @Inject constructor(
    private val firebaseUsersService: FirebaseUsersService
){
    fun saveUser(user: User, callback: (data: Resource<Boolean>) -> Unit){
        firebaseUsersService.saveUser(user){ callback(it) }
    }

    fun usernameExists(username:String, callback: (data: Resource<Boolean>) -> Unit) {
        firebaseUsersService.usernameExists(username){ callback(it) }
    }

    fun saveUsername(username:String, email:String, callback: (data: Resource<Boolean>) -> Unit){
        firebaseUsersService.saveUsername(username, email){ callback(it) }
    }
    fun deleteUser(username:String, callback: (data: Resource<Boolean>) -> Unit){
        firebaseUsersService.deleteUser(username){ callback(it) }
    }

    fun getUserDocument(username: String, callback: (data: Resource<User>) -> Unit){
        firebaseUsersService.getUserDocument(username) { callback(it) }
    }
    fun getUsernameDocumentId(email:String, callback: (data: Resource<String>)->Unit){
        firebaseUsersService.getUsernameDocumentId(email){ callback(it) }
    }
}