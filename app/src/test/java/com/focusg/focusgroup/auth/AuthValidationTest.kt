package com.focusg.focusgroup.auth

import com.focusg.focusgroup.domain.validation.AuthValidation
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class AuthValidationTest {
    private lateinit var authValidator: AuthValidation

    @Before
    fun setUp(){
        authValidator = AuthValidation()
    }



    @Test
    fun isValidEmail_isValid_ReturnTrue(){
        var isValid = authValidator.isValidEmail("simple@example.com")
        assertThat(isValid).isTrue()
        isValid = authValidator.isValidEmail("disposable.style.email.with+symbol@example.com")
        assertThat(isValid).isTrue()
        isValid = authValidator.isValidEmail("example-indeed@strange-example.com")
        assertThat(isValid).isTrue()
    }

    @Test
    fun isValidEmail_isInValid_ReturnFalse(){
        var isInvalid = authValidator.isValidEmail("Abc.example.com")
        assertThat(isInvalid).isFalse()
        isInvalid = authValidator.isValidEmail("a\"b(c)d,e:f;g<h>i[j\\k]l@example.com")
        assertThat(isInvalid).isFalse()
        isInvalid = authValidator.isValidEmail("this is\"not\\allowed@example.com")
        assertThat(isInvalid).isFalse()
    }

    @Test
    fun isValidPassword_isValid_ReturnTrue(){
        var isValid = authValidator.isValidPassword("soyour&passWord1")
        assertThat(isValid).isTrue()

        isValid = authValidator.isValidPassword("SterlingGmail20.15")
        assertThat(isValid).isTrue()

        isValid = authValidator.isValidPassword("!Lov3MyPiano")
        assertThat(isValid).isTrue()

    }

    @Test
    fun isValidPassword_isInvalid_ReturnFalse(){
        var isValid = authValidator.isValidPassword("abcde")
        assertThat(isValid).isFalse()
        isValid = authValidator.isValidPassword("whether the components in your app return the expected results, we recommend using the Truth library and classes from Android Assertions, as shown in the preceding example. To learn more about the types of logic validation that Truth and Android Assertions support")
        assertThat(isValid).isFalse()
        isValid = authValidator.isValidPassword("Lov3MyPiano")
        assertThat(isValid).isFalse()
        isValid = authValidator.isValidPassword("!LovMyPiano")
        assertThat(isValid).isFalse()
        isValid = authValidator.isValidPassword("!lov3mypiano")
        assertThat(isValid).isFalse()
    }

    @Test
    fun isValidUsername_IsValid_ReturnTrue(){
        var isValid = authValidator.isValidUsername("comeandsee")
        assertThat(isValid).isTrue()
        isValid = authValidator.isValidUsername("comeandsee5")
        assertThat(isValid).isTrue()
        isValid = authValidator.isValidUsername("come_and_see5")
        assertThat(isValid).isTrue()
    }

    @Test
    fun isValidUsername_IsInvalid_ReturnFalse(){
        var isValid = authValidator.isValidUsername("come")
        assertThat(isValid).isFalse()
        isValid = authValidator.isValidUsername("_come")
        assertThat(isValid).isFalse()
        isValid = authValidator.isValidUsername("come_")
        assertThat(isValid).isFalse()

    }
}