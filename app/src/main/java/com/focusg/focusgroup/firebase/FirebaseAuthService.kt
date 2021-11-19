package com.focusg.focusgroup.firebase

import android.util.Log
import com.focusg.focusgroup.common.Resource
import com.focusg.focusgroup.data.remote.dto.UserDto
import com.focusg.focusgroup.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirebaseAuthService @Inject constructor(

) {
    val auth: FirebaseAuth = Firebase.auth


    fun signInWithEmailAndPassword(email:String, password:String, callback: (data: Resource<Boolean>) -> Unit){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                callback(Resource.Success<Boolean>(true))
            } else {
                callback(Resource.Error<Boolean>("Authentication failed"))
            }
        }.addOnFailureListener { callback(Resource.Error<Boolean>(it.message.toString()))}
    }

    fun emailExists(email: String, callback: (data: Resource<List<String>>?) -> Unit){
        auth.fetchSignInMethodsForEmail(email).addOnSuccessListener {callback(Resource.Success<List<String>>(it.signInMethods?: emptyList()))}
            .addOnFailureListener { callback(Resource.Error<List<String>>(data = emptyList(), message = "An unexpected error occurred.")) }
    }

    fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        callback: (data: Resource<Boolean>) -> Unit
    ){
       auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                callback(Resource.Success<Boolean>(true))

            } else {
                task.exception?.let {
                    val message = it.message.toString()
                    callback(Resource.Error<Boolean>(message = message))
                }
            }
        }
    }


}