package com.focusg.focusgroup.domain.use_case.users

import com.focusg.focusgroup.common.Resource
import com.focusg.focusgroup.domain.repository.UsersRepository
import javax.inject.Inject

class GetUsernameIdUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {

    suspend operator fun invoke(email:String, callback: (data: Resource<String>) -> Unit){
        usersRepository.getUsernameDocumentId(email){
            callback(it)
        }
    }
}