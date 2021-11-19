package com.focusg.focusgroup.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.focusg.focusgroup.data.database.dao.UserDao
import com.focusg.focusgroup.domain.model.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}