package com.focusg.focusgroup.presentation.focus_groups.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.focusg.focusgroup.R
import com.focusg.focusgroup.databinding.FragmentCreateFocusGroupBinding
import com.focusg.focusgroup.presentation.auth.AuthValidationViewModel
import com.focusg.focusgroup.presentation.focus_groups.FocusGroupValidationViewModel
import com.focusg.focusgroup.presentation.util.hideControlErrors


class CreateFocusGroupFragment : Fragment() {

    lateinit var binding: FragmentCreateFocusGroupBinding
    private val validationViewModel: FocusGroupValidationViewModel  by viewModels()

    var isValidName: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_focus_group, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }



    private fun nameSetUp(editText: EditText, textView: TextView){
        editText.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            validationViewModel.validateName(text.toString())
            hideControlErrors(textView)
        })
        validationViewModel.isValidName.observe(viewLifecycleOwner){ isValidName = it }
    }

}