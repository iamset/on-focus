package com.focusg.focusgroup.domain.use_case.users

import com.focusg.focusgroup.domain.model.User
import com.focusg.focusgroup.domain.repository.UsersRepository
import javax.inject.Inject

class CacheCurrentUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {

    suspend operator fun  invoke(user: User){
        usersRepository.cacheCurrentUser(user)
    }
}