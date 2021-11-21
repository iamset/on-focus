package com.focusg.focusgroup.domain.validation

import javax.inject.Inject

class FocusGroupValidation @Inject constructor() {

    fun isValidName(name:String):Boolean{
        val processedName = name.trim()
        return when{
            processedName.isBlank() ||processedName.length > 150
                -> false
            else -> true
        }
    }

    fun isValidDescription(description:String): Boolean{
        val processedDesc = description.trim()
        return when{
            processedDesc.isBlank() || processedDesc.length > 1000 -> false
            else -> true
        }
    }

    fun isValidParticipantsLimit(limit: Int): Boolean{
        return when{
            limit == 0 || limit < 0 -> false
            else ->  true
        }

    }
}