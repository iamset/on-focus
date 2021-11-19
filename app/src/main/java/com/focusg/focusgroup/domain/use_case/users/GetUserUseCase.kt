package com.focusg.focusgroup.domain.use_case.users

import com.focusg.focusgroup.common.Resource
import com.focusg.focusgroup.domain.model.User
import com.focusg.focusgroup.domain.repository.UsersRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {

    suspend operator fun invoke(username:String, callback: (data: Resource<User>) -> Unit){
        usersRepository.getUserDocument(username){ callback(it) }
    }
}