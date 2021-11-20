package com.focusg.focusgroup.presentation.focus_groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.focusg.focusgroup.domain.use_case.focus_groups.validate_focus_group.ValidateNameUseCase
import javax.inject.Inject

class FocusGroupValidationViewModel @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase
):ViewModel() {

    private val _isValidName = MutableLiveData<Boolean>()
    val isValidName : LiveData<Boolean> = _isValidName

    fun validateName(name:String){
        _isValidName.value = validateNameUseCase(name)
    }
}