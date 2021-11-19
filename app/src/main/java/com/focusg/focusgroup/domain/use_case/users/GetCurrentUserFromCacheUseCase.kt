package com.focusg.focusgroup.domain.use_case.users

import com.focusg.focusgroup.domain.model.User
import com.focusg.focusgroup.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserFromCacheUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(): User{
        return usersRepository.getCurrentUserFromCache()
    }
}