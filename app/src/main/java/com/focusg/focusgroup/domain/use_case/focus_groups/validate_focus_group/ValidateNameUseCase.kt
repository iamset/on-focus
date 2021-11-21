package com.focusg.focusgroup.domain.use_case.focus_groups.validate_focus_group

import com.focusg.focusgroup.domain.validation.FocusGroupValidation
import javax.inject.Inject

class ValidateNameUseCase @Inject constructor(
    private val validator: FocusGroupValidation
) {
    operator fun invoke(name: String) = validator.isValidName(name)
}