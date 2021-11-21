package com.focusg.focusgroup.focus_groups

import com.focusg.focusgroup.domain.validation.FocusGroupValidation
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class FocusGroupsValidationTest {

    lateinit var validator: FocusGroupValidation

    @Before
    fun setUp(){
        validator = FocusGroupValidation()
    }

    @Test
    fun isValidName_isValid_ReturnTrue(){
        val valid = validator.isValidName("Let's talk about success")
        assertThat(valid).isTrue()
    }

    @Test
    fun isValidName_isNotValid_ReturnFalse(){
        var valid = validator.isValidName("")
        assertThat(valid).isFalse()
        valid = validator.isValidName("    ")
        assertThat(valid).isFalse()

        //165 characters
        valid = validator.isValidName("Diam a id varius nostra adipiscing vel quam tincidunt lacinia ultrices scelerisque habitasse quisque a scelerisque ipsum nulla velit suspendisse a scelerisque velit.")
        assertThat(valid).isFalse()
    }

    @Test
    fun isValidDescription_isValid_ReturnTrue(){
        var valid = validator.isValidDescription("Lorem ipsum dolor sit amet, ius augue omnium ut. Sit possim deserunt at. Vel harum errem ne, qui ullum voluptatum.")
        assertThat(valid).isTrue()
    }

    @Test
    fun isValidDescription_isNotValid_ReturnFalse(){
        var valid = validator.isValidDescription("")
        assertThat(valid).isFalse()
        valid = validator.isValidDescription("   ")
        assertThat(valid).isFalse()
        // 1200 characters
        valid = validator.isValidDescription("Viverra vehicula vestibulum orci sit parturient eu ipsum a scelerisque himenaeos justo faucibus parturient ante pharetra ante faucibus a consectetur ad at scelerisque facilisis convallis. " +
                "Est vestibulum cursus diam fringilla vestibulum nunc sociosqu mi mauris a cubilia etiam libero a ullamcorper nisl a. Eu ac sodales cubilia a scelerisque ultricies hendrerit id scelerisque ullamcorper morbi id habitant vulputate ridiculus sociis tincidunt. " +
                "Interdum adipiscing adipiscing sit suspendisse eget hendrerit ante scelerisque condimentum erat vestibulum parturient ad suspendisse.\n" +
                "\n" +
                "Dui vestibulum dictum." +
                "Viverra vehicula vestibulum orci sit parturient eu ipsum a scelerisque himenaeos justo faucibus parturient ante pharetra ante faucibus a consectetur ad at scelerisque facilisis convallis. \" +\n" +
                "                \"Est vestibulum cursus diam fringilla vestibulum nunc sociosqu mi mauris a cubilia etiam libero a ullamcorper nisl a. Eu ac sodales cubilia a scelerisque ultricies hendrerit id scelerisque ullamcorper morbi id habitant vulputate ridiculus sociis tincidunt. \" +\n" +
                "                \"Interdum adipiscing adipiscing sit suspendisse eget hendrerit ante scelerisque condimentum erat vestibulum parturient ad suspendisse.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Dui vestibulum dictum.")
        assertThat(valid).isFalse()
    }

    @Test
    fun isValidParticipantsLimit_isValid_ReturnTrue(){
        var valid = validator.isValidParticipantsLimit(20)
        assertThat(valid).isTrue()
    }

    @Test
    fun isValidParticipantsLimit_isNotValid_ReturnFalse(){
        var valid = validator.isValidParticipantsLimit(0)
        assertThat(valid).isFalse()
        valid = validator.isValidParticipantsLimit(-1)
        assertThat(valid).isFalse()
    }
}