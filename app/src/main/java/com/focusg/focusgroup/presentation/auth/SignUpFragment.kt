package com.focusg.focusgroup.presentation.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.focusg.focusgroup.R
import com.focusg.focusgroup.databinding.FragmentSignUpBinding
import com.focusg.focusgroup.domain.util.AuthFormControl
import com.focusg.focusgroup.presentation.users.UsersViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import com.focusg.focusgroup.presentation.MainActivity
import com.focusg.focusgroup.presentation.util.hideKeyboard
import java.lang.Exception


@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val authValidationViewModel: AuthValidationViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val usersViewModel : UsersViewModel by viewModels()

    lateinit var binding: FragmentSignUpBinding

    var isValidPassword: Boolean? = null
    var isValidConfirmPassword: Boolean? = null
    var isValidUsername : Boolean?= null
    var isValidEmail: Boolean? = null
    var usernameExists: Boolean? = null
    var emailExists: Boolean? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        controlsSetUp(binding.usernameSignup, binding.usernameSignupError, AuthFormControl.USERNAME.control)
        controlsSetUp(binding.emailSignup, binding.emailSignupError, AuthFormControl.EMAIL.control)
        controlsSetUp(binding.passwordSignup, binding.passwordSignupError, AuthFormControl.PASSWORD.control)
        controlsSetUp(binding.confirmPasswordSignup, binding.confirmPasswordSignupError, AuthFormControl.CONFIRM_PASSWORD.control)
        controlsSetUp(binding.usernameSignup, binding.usernameSignupError2, AuthFormControl.USERNAME.control)
        signUpButtonSetUp()
        observeFirebaseError()
        observeIsLoading()
        observerCurrentUser()
        navigateToSignIn()
    }



    private fun navigateToSignIn(){
        binding.orSignIn.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
            findNavController().navigate(action)
        }
    }

    private fun observerCurrentUser(){
        authViewModel.currentUser.observe(viewLifecycleOwner){
            it?.let {
                navigateToFocusGroups()
            }
        }
    }

    private fun navigateToFocusGroups(){
        val action = SignUpFragmentDirections.actionSignUpFragmentToFocusGroupListFragment()
        findNavController().navigate(action)
    }

    private fun observeIsLoading(){
        authViewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            if (isLoading){
                binding.signup.text = getString(R.string.loading)
                binding.signup.isEnabled = false
            } else {
                binding.signup.text = getString(R.string.sign_up)
                binding.signup.isEnabled = true
            }
        }
    }

    private fun observeFirebaseError(){
        authViewModel.errorMessage.observe(viewLifecycleOwner){ error ->
            if(error != null){
                binding.signupError.text = error
                showControlErrors(binding.signupError)
            }
        }

    }





    private fun controlsSetUp(editText: EditText, textView: TextView, type:String){
        editText.setOnFocusChangeListener { _, hasFocus ->
            editTextHasFocus(hasFocus, textView, type)
        }
        when(type){
            AuthFormControl.USERNAME.control -> usernameSetUp(editText, textView, binding.usernameSignupError2)
            AuthFormControl.EMAIL.control -> emailSetUp(editText, textView, binding.emailSignupError2)
            AuthFormControl.PASSWORD.control -> passwordSetUp(editText, textView)
            AuthFormControl.CONFIRM_PASSWORD.control -> confirmPasswordSetUp(editText, textView)
        }
    }

    private fun confirmPasswordSetUp(editText: EditText, textView: TextView){
        editText.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            authValidationViewModel.validateConfirmPassword(binding.passwordSignup.text.toString(),text.toString())
            hideControlErrors(textView)
        })
        authValidationViewModel.isValidConfirmPassword.observe(viewLifecycleOwner){ isValidConfirmPassword = it }
    }

    private fun passwordSetUp(editText: EditText, textView: TextView){
        editText.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            authValidationViewModel.validatePassword(text.toString())
            hideControlErrors(textView)
        })
        authValidationViewModel.isValidPassword.observe(viewLifecycleOwner){ isValidPassword = it }
    }

    private fun emailSetUp(editText: EditText, textView: TextView, textView2: TextView){
        editText.addTextChangedListener(onTextChanged = {text, _, _, _ ->
            authValidationViewModel.validateEmail(text.toString())
            hideControlErrors(textView)
            hideControlErrors(textView2)
            // make api call
            if(isValidEmail == true){
                authViewModel.checkIfEmailExists(text.toString())
            }
        })
        authValidationViewModel.isValidEmail.observe(viewLifecycleOwner){ isValidEmail = it }
        authViewModel.emailExists.observe(viewLifecycleOwner){
            emailExists = it
            if(it){
                showControlErrors(binding.emailSignupError2)
            } else hideControlErrors(binding.emailSignupError2)
        }
    }


    private fun usernameSetUp(editText: EditText, textView: TextView, textView2: TextView){
        editText.addTextChangedListener(onTextChanged = {text, _, _, _ ->
            authValidationViewModel.validateUsername(text.toString())
            hideControlErrors(textView)
            hideControlErrors(textView2)
            // make api call
            if(isValidUsername == true){
                usersViewModel.checkIfUsernameExists(text.toString())
            }

        })
        authValidationViewModel.isValidUsername.observe(viewLifecycleOwner){ isValidUsername = it }
        usersViewModel.usernameExists.observe(viewLifecycleOwner){
            usernameExists = it
            if(it){
                showControlErrors(binding.usernameSignupError2)
            } else {
                hideControlErrors(binding.usernameSignupError2)
            }
        }
    }



    private fun editTextHasFocus(hasFocus: Boolean, textView: TextView, type: String){
        if (hasFocus){
            hideControlErrors(textView)
        } else {
            when(type){
                AuthFormControl.USERNAME.control -> decideIfToShowError(binding.usernameSignup, isValidUsername, binding.usernameSignupError, getString(R.string.username_should_be_within_5_20_characters_and_should_not_consists_of_special_characters_at_both_ends))
                AuthFormControl.EMAIL.control -> decideIfToShowError(binding.emailSignup, isValidEmail, binding.emailSignupError, getString(R.string.enter_a_valid_email))
                AuthFormControl.PASSWORD.control -> decideIfToShowError(binding.passwordSignup, isValidPassword, binding.passwordSignupError, getString(R.string.password_should_be_between_12_to_50_characters_and_contain_at_least_1_number_1_special_and_1_uppercase_character))
                AuthFormControl.CONFIRM_PASSWORD.control -> decideIfToShowError(binding.confirmPasswordSignup, isValidConfirmPassword, binding.confirmPasswordSignupError, getString(R.string.passwords_do_not_match))

            }
        }
    }





    private fun signUpButtonSetUp(){

        binding.signup.setOnClickListener {
            hideKeyboard()
            authViewModel.resetError()
            decideIfToShowError(binding.usernameSignup, isValidUsername, binding.usernameSignupError, getString(R.string.username_should_be_within_5_20_characters_and_should_not_consists_of_special_characters_at_both_ends))
            decideIfToShowError(binding.emailSignup, isValidEmail, binding.emailSignupError, getString(R.string.enter_a_valid_email))
            decideIfToShowError(binding.passwordSignup, isValidPassword, binding.passwordSignupError, getString(R.string.password_should_be_between_12_to_50_characters_and_contain_at_least_1_number_1_special_and_1_uppercase_character))
            decideIfToShowError(binding.confirmPasswordSignup, isValidConfirmPassword, binding.confirmPasswordSignupError,getString(R.string.passwords_do_not_match))

            authValidationViewModel.validateFormControls()
            authValidationViewModel.isValidFormControls.observe(viewLifecycleOwner){
                // sign up if form is valid and username and email does not exist.
                if (it == true && usernameExists == false && emailExists == false){
                    authViewModel.signUpWithEmailAndPassword(
                        email = binding.emailSignup.text.toString(),
                        password = binding.passwordSignup.text.toString(),
                        username = binding.usernameSignup.text.toString()
                    )

                }
            }


        }
    }

    private fun decideIfToShowError(control: EditText, isValidControl: Boolean?, textView: TextView, error: String){
        if(isValidControl == null){
            showControlErrors(textView)

        }
        if(isValidControl == true){
            hideControlErrors(textView)
        }
        if (isValidControl == false && control.text.toString().isBlank()) {
            typeOfControlError(textView,getString(R.string.required))
            showControlErrors(textView)
        }
        if (isValidControl == false && control.text.toString().isNotBlank()) {
            typeOfControlError(textView,error)
            showControlErrors(textView)
        }
    }

    private fun showControlErrors(textView: TextView){
        textView.visibility = View.VISIBLE
    }

    private fun hideControlErrors(textView: TextView){
        textView.visibility = View.GONE
    }

    private fun typeOfControlError(textView: TextView, error: String){
        textView.text = error
    }


}



