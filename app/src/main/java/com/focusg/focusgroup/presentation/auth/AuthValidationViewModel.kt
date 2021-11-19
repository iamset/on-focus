package com.focusg.focusgroup.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusg.focusgroup.domain.use_case.auth.validate_auth.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthValidationViewModel @Inject constructor(
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase
): ViewModel() {

    private val _isValidPassword = MutableLiveData<Boolean>(null)
    val isValidPassword: LiveData<Boolean> = _isValidPassword


    private val _isValidUsername = MutableLiveData<Boolean>(null)
    val isValidUsername: LiveData<Boolean> = _isValidUsername

    private val _isValidEmail = MutableLiveData<Boolean>(null)
    val isValidEmail: LiveData<Boolean> = _isValidEmail

    private val _isValidConfirmPassword = MutableLiveData<Boolean>(null)
    val isValidConfirmPassword: LiveData<Boolean> = _isValidConfirmPassword

    private val _isValidFormControls = MutableLiveData(false)
    val isValidFormControls: LiveData<Boolean> = _isValidFormControls



    fun validatePassword(password: String){
        _isValidPassword.value = validatePasswordUseCase(password.trim())
    }



    fun validateUsername(username: String){
        _isValidUsername.value = validateUsernameUseCase(username.trim())
        if(_isValidUsername.value == true){
            viewModelScope.launch {
                delay(500)
            }

        }
    }

    fun validateEmail(email: String){
        _isValidEmail.value = validateEmailUseCase(email.trim())
    }

    fun validateConfirmPassword(password1:String, password2:String){
        _isValidConfirmPassword.value = validateConfirmPasswordUseCase(password1.trim(), password2.trim())
    }

    fun validateFormControls(){

        val validUsername = _isValidUsername.value?: false
        val validEmail = _isValidEmail.value?: false
        val validPassword = _isValidPassword.value?: false
        val validConfirmPassword = _isValidConfirmPassword.value?:false

        _isValidFormControls.value =  validUsername
                && validEmail && validPassword && validConfirmPassword
    }



}