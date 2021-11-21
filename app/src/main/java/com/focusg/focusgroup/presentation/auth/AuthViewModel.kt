package com.focusg.focusgroup.presentation.auth

import android.content.SharedPreferences
import android.provider.Settings.Secure.getString

import android.util.Log

import androidx.lifecycle.*
import com.focusg.focusgroup.R
import com.focusg.focusgroup.common.Resource
import com.focusg.focusgroup.domain.model.User
import com.focusg.focusgroup.domain.use_case.auth.signin.SignInUseCase
import com.focusg.focusgroup.domain.use_case.auth.signup.EmailExistsUseCase
import com.focusg.focusgroup.domain.use_case.auth.signup.SignUpUseCase
import com.focusg.focusgroup.domain.use_case.users.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val cacheCurrentUserUseCase: CacheCurrentUserUseCase,
    private val getCurrentUserFromCacheUseCase: GetCurrentUserFromCacheUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val saveUsernameUseCase: SaveUsernameUseCase,
    private val emailExistsUseCase: EmailExistsUseCase,
    private val signInUseCase: SignInUseCase,
    private val getUsernameIdUseCase: GetUsernameIdUseCase,
    private val getUserUseCase: GetUserUseCase
): ViewModel() {

    private val _currentUser : MutableLiveData<User?> = MutableLiveData()
    val currentUser: LiveData<User?> = _currentUser

    private val _emailExists: MutableLiveData<Boolean> = MutableLiveData()
    val emailExists: LiveData<Boolean> = _emailExists

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        viewModelScope.launch {
            // this is only available here, so that I don't have to sign in
            // each time i reinstall the app.
//            _currentUser.value = getCurrentUserFromCacheUseCase()
        }
    }


    // LOGIN FLOW WITH EMAIL AND PASSWORD
    fun signInWithEmailAndPassword(email:String, password:String){
        viewModelScope.launch {
            signInUseCase(email, password){
                _isLoading.value = true
                when(it){
                    is Resource.Success -> {
                        // get Username document
                        getUsername(email)
                    }
                    is Resource.Error -> {
                        // implement this: log out
                        setErrors(it.message.toString())
                    }
                }
            }
        }
    }

    private fun getUsername(email: String){
        viewModelScope.launch {
            getUsernameIdUseCase(email){
                when(it){
                    is Resource.Success -> {
                        getUserDocument(it.data.toString())
                    }
                    is Resource.Error -> {
                        setErrors(it.message.toString())
                    }
                }
            }
        }
    }

    private fun getUserDocument(username: String){
        viewModelScope.launch {
            getUserUseCase(username){
                when(it){
                    is Resource.Success -> {
                        // cache current user
                        val user = it.data?: User()
                        cacheCurrentUser(user)
                    }
                    is Resource.Error -> {
                        setErrors(it.message.toString())
                    }
                }
            }
        }
    }


    // SIGIN FLOW WITH JUST USERNAME AND PASSWORD
    fun signInWithUsernameAndPassword(username: String, password: String){
        viewModelScope.launch {
            _isLoading.value = true
            getUserUseCase(username){
                when(it){
                    is Resource.Success -> {
                        val user = it.data?:User()
                        val email = user.email.toString()
                        signIn(email, password, user)
                    }
                    is Resource.Error -> {
                        setErrors(it.message.toString())
                    }
                }
            }
        }
    }

    private fun signIn(email:String, password: String, user:User){
        viewModelScope.launch {
            signInUseCase(email, password){
                when(it){
                    is Resource.Success -> {
                        cacheCurrentUser(user)
                    }
                    is Resource.Error -> {
                        setErrors(it.message.toString())
                    }
                }
            }
        }
    }

    // SIGN UP FLOW
    fun signUpWithEmailAndPassword(
        email:String,
        password:String,
        username:String
    ){
        viewModelScope.launch {
            saveUsernameUseCase(username, email){
                _isLoading.value = true
                when(it){
                    is Resource.Success -> {
                        createUserDocument(email, username, password)
                    }
                    is Resource.Error -> {
                        setErrors(it.message.toString())
                    }
                }
            }
        }
    }

    private fun createUserDocument(email: String, username: String, password: String){
        viewModelScope.launch {
            val user = User(email = email, username = username)
            saveUserUseCase(user){
                when(it) {
                    is Resource.Success -> {signUpUser(email, password, user)}
                    is Resource.Error -> {setErrors(it.message.toString())}
                }
            }
        }
    }
    private fun signUpUser(email: String, password: String, user:User){
        viewModelScope.launch {
            signUpUseCase(email, password){
                when(it){
                    is Resource.Success -> cacheCurrentUser(user)
                    is Resource.Error -> {setErrors(it.message.toString())}
                }
            }
        }
    }

    fun checkIfEmailExists(email:String){
        viewModelScope.launch {
            emailExistsUseCase(email){
                _emailExists.value = it
                viewModelScope.launch { delay(500) }
            }
        }
    }
    private fun cacheCurrentUser(user:User){
        viewModelScope.launch {
            cacheCurrentUserUseCase(user)
            _currentUser.value = user
            _isLoading.value = false
        }
    }

    fun resetError(){
        _errorMessage.value = null
    }

    private fun setErrors(message: String){
        _isLoading.value = false
        _errorMessage.value = message
        Log.d("Result", message)
    }

}