package com.focusg.focusgroup.domain.service

import android.util.Log
import com.focusg.focusgroup.data.database.dao.UserDao
import com.focusg.focusgroup.domain.model.User
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class CacheService @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun cacheCurrentUser(user: User){
        userDao.insertUser(user)
    }

    suspend fun getCurrentUserFromCache(): User {
        val userFlow = userDao.loadUser().first()
        return userFlow.getOrElse(0) { User() }
    }
}