package com.focusg.focusgroup.presentation.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.focusg.focusgroup.R
import com.focusg.focusgroup.domain.util.AuthFormControl


fun Fragment.hideKeyboard() {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(requireView().windowToken, 0)
}

fun Fragment.showControlErrors(textView: TextView) {
    textView.visibility = View.VISIBLE
}

fun Fragment.hideControlErrors(textView: TextView) {
    textView.visibility = View.GONE
}

fun Fragment.typeOfControlError(textView: TextView, error: String) {
    textView.text = error
}

fun Fragment.decideIfToShowError(
    control: EditText,
    isValidControl: Boolean?,
    textView: TextView,
    error: String
) {
    if (isValidControl == null) {
        showControlErrors(textView)

    }
    if (isValidControl == true) {
        hideControlErrors(textView)
    }
    if (isValidControl == false && control.text.toString().isBlank()) {
        typeOfControlError(textView, getString(R.string.required))
        showControlErrors(textView)
    }
    if (isValidControl == false && control.text.toString().isNotBlank()) {
        typeOfControlError(textView, error)
        showControlErrors(textView)
    }
}

fun Fragment.editTextHasFocus(
    hasFocus: Boolean,
    textView: TextView,
    formType:() -> Unit
){
    if (hasFocus){
        hideControlErrors(textView)
    } else  {
        formType()
    }
}



