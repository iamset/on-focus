package com.focusg.focusgroup.firebase

import com.focusg.focusgroup.common.Resource
import com.focusg.focusgroup.common.USERNAMES
import com.focusg.focusgroup.common.USERS
import com.focusg.focusgroup.domain.model.User
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import javax.inject.Inject

class FirebaseUsersService @Inject constructor(

) {
    val firestore: FirebaseFirestore = Firebase.firestore
    val usersRef = firestore.collection(USERS)
    var usernameRef = firestore.collection(USERNAMES)

    fun saveUser(user: User, callback: (data: Resource<Boolean>) -> Unit){
        val (username) = user
        usersRef.document(username).set(user)
            .addOnSuccessListener { callback(Resource.Success<Boolean>(true)) }
            .addOnFailureListener { callback(Resource.Error<Boolean>(data = false, message = it.localizedMessage)) }
    }



    fun usernameExists(username:String, callback: (data: Resource<Boolean>) -> Unit) {
        var usernameDocument = usernameRef.document(username)

        usernameDocument.get().addOnSuccessListener {
            if(it != null && it.exists()) callback(Resource.Success<Boolean>(true))
            else callback(Resource.Success<Boolean>(false))
        }.addOnFailureListener { callback(Resource.Error<Boolean>(it.localizedMessage.toString()))  }

    }

    fun saveUsername(username:String, email:String, callback: (data: Resource<Boolean>) -> Unit){
        usernameRef
            .document(username)
            .set(hashMapOf("email" to email))
            .addOnSuccessListener { callback(Resource.Success<Boolean>(true)) }
            .addOnFailureListener { callback(Resource.Error<Boolean>(message =it.localizedMessage, data = false)) }

    }

    fun deleteUser(username:String, callback: (data: Resource<Boolean>) -> Unit){
        usersRef.document(username)
            .delete()
            .addOnSuccessListener { callback(Resource.Success<Boolean>(true)) }
            .addOnFailureListener { callback(Resource.Error<Boolean>(it.message.toString(), false)) }
    }

    fun getUserDocument(username: String, callback: (data: Resource<User>) -> Unit){
        usersRef.document(username).get().addOnSuccessListener { document ->
            if (document != null){
                if(document.exists()){
                    val user = User(
                        username = document.getString("username")?:"",
                        photoUrl = document.getString("photoUrl")?:"",
                        email = document.getString("email")?:""
                    )
                    callback(Resource.Success<User>(user))
                } else {
                    callback(Resource.Error<User>("User not found"))
                }

            } else callback(Resource.Error<User>("User not found"))
        }.addOnFailureListener { Resource.Error<User>(it.localizedMessage) }
    }

    // username points to the email
    fun getUsernameDocumentId(email:String, callback: (data: Resource<String>)->Unit){
        usernameRef.whereEqualTo("email", email).get().addOnSuccessListener { documents ->
            if(documents.isEmpty){
                callback(Resource.Error<String>("No such username exist"))
            } else {
                for (document in documents){
                    callback(Resource.Success<String>(document.id))
                    break
                }
            }

        }.addOnFailureListener {
            callback(Resource.Error<String>(it.localizedMessage))
        }
    }



}