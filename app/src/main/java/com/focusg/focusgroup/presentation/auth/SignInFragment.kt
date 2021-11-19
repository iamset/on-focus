package com.focusg.focusgroup.presentation.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.focusg.focusgroup.R
import com.focusg.focusgroup.databinding.FragmentSignInBinding
import com.focusg.focusgroup.presentation.users.UsersViewModel
import com.focusg.focusgroup.presentation.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    lateinit var binding: FragmentSignInBinding

    private val authViewModel: AuthViewModel by viewModels()
    private val authValidationViewModel: AuthValidationViewModel by viewModels()
    private val usersViewModel : UsersViewModel by viewModels()
    var isValidEmail:Boolean? = null
    var isValidUsername:Boolean? = null
    var isValidPassword: Boolean? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeUsernameEmailPassword()
        signInButtonClicked()
        observeCurrentUser()
        observeFirebaseError()
        observeIsLoading()
        orSignUpTextClicked()
    }

    private fun observeIsLoading(){
        authViewModel.isLoading.observe(viewLifecycleOwner){
            binding.sign.isEnabled = !it
        }
    }

    private fun observeUsernameEmailPassword(){
        authValidationViewModel.isValidEmail.observe(viewLifecycleOwner){
            isValidEmail = it
        }
        authValidationViewModel.isValidUsername.observe(viewLifecycleOwner){
            isValidUsername = it
        }
        authValidationViewModel.isValidPassword.observe(viewLifecycleOwner){
            isValidPassword = it
        }
    }

    private fun observeFirebaseError(){
        authViewModel.errorMessage.observe(viewLifecycleOwner){
            it?.let { error ->
                binding.signinError.text = error
                binding.signinError.visibility = View.VISIBLE
            }
        }
    }

    private fun observeCurrentUser(){
        authViewModel.currentUser.observe(viewLifecycleOwner){
            it?.let {
                if(it.username.isNotBlank()){
                    val action = SignInFragmentDirections.actionSignInFragmentToFocusGroupListFragment()
                    findNavController().navigate(action)
                }

            }

        }
    }

    private fun orSignUpTextClicked(){
        binding.signinOrSignup.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
    }

    private fun signInButtonClicked(){

        binding.sign.setOnClickListener {
            hideKeyboard()
            authValidationViewModel.validateEmail(binding.usernameEmailSignin.text.toString())
            authValidationViewModel.validateUsername(binding.usernameEmailSignin.text.toString())

            val usernameEmailText = binding.usernameEmailSignin.text.toString()
            val passwordText = binding.passwordSignin.text.toString()
            // No need to validate the sign in form.
            if(isValidEmail== true){
                authViewModel.signInWithEmailAndPassword(usernameEmailText, passwordText)
            } else if(isValidUsername == true){
                authViewModel.signInWithUsernameAndPassword(usernameEmailText, passwordText)
            }
        }
    }


}