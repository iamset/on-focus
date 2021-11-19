package com.focusg.focusgroup.domain.use_case.users

import com.focusg.focusgroup.common.Resource
import com.focusg.focusgroup.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsernameExistsUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend  operator fun invoke(username:String, callback: (data: Resource<Boolean>) -> Unit){
     return usersRepository.usernameExists(username){
         callback(it)
     }
   }
}