package com.focusg.focusgroup.presentation.users

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusg.focusgroup.common.Resource
import com.focusg.focusgroup.domain.use_case.users.UsernameExistsUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usernameExistsUseCase: UsernameExistsUseCase

): ViewModel() {
    private val _usernameExists: MutableLiveData<Boolean> = MutableLiveData(false)
    val usernameExists: LiveData<Boolean> = _usernameExists


    fun checkIfUsernameExists(username: String){
        viewModelScope.launch {
            usernameExistsUseCase(username){
                when(it){
                    is Resource.Success -> {
                        if(it.data == true){
                            _usernameExists.value = true
                            delayRequest()
                        } else if(it.data == false){
                            _usernameExists.value = false
                            delayRequest()
                        }
                    }
                    is Resource.Error -> {
                        _usernameExists.value = false
                    }
                }
            }
        }
    }

    fun delayRequest(){
        viewModelScope.launch {
            delay(500)
        }
    }


}