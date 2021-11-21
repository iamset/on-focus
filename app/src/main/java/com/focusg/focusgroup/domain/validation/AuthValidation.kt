package com.focusg.focusgroup.domain.validation

import javax.inject.Inject

class AuthValidation @Inject constructor() {


    fun isValidConfirmPassword(password1:String, password2: String): Boolean{
        return password1 == password2
    }

    fun isValidPassword(password: String): Boolean {
        var isValidLength = false
        var isValid = BooleanArray(3) {false}

        isValidLength = when{
            password.length < 12 -> false
            password.length > 50 -> false
            else -> true
        }

        if(!isValidLength) return false

        for (p in password){
            // special characters
            if (p.code in 32..47 || p.code in 58..64
                || p.code in 90..96 || p.code in 123..126){
                isValid[0] = true

            }
            // numbers
            if(p.code in 48..57){
                isValid[1] = true
            }
            // upper case.
            if (p.code in 65..90){
                isValid[2] = true
            }
        }

        return isValid[0] && isValid[1] && isValid[2]
    }

//    Username consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase.
//    Username allowed can consists of the dot (.), underscore (_), and hyphen (-).
//    The dot (.), underscore (_), or hyphen (-) must not be the first or last character.
//    The dot (.), underscore (_), or hyphen (-) does not appear consecutively, e.g., java..regex
//    The number of characters must be between 5 to 20.
    fun isValidUsername(username: String) = username.lowercase().matches(Regex("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]\$"))

    fun isValidEmail(email: String) = email.matches(Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))
}