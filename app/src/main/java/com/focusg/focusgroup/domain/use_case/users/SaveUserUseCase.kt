package com.focusg.focusgroup.domain.use_case.users

import com.focusg.focusgroup.common.Resource
import com.focusg.focusgroup.domain.model.User
import com.focusg.focusgroup.domain.repository.UsersRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {

    suspend operator fun invoke(user: User, callback: (data: Resource<Boolean>) -> Unit){
        usersRepository.saveUser(user){
            callback(it)
        }
    }
}