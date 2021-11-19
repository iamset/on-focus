package com.focusg.focusgroup.domain.use_case.users

import com.focusg.focusgroup.common.Resource
import com.focusg.focusgroup.domain.repository.UsersRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {

    suspend operator fun invoke(username:String, callback: (data: Resource<Boolean>) -> Unit){
        usersRepository.deleteUser(username){
            callback(it)
        }
    }
}