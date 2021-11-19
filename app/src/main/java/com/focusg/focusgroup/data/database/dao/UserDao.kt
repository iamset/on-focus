package com.focusg.focusgroup.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.focusg.focusgroup.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // this ensures that only one row will be in the table
    // since the primary key is username.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    //will get the first row from the table as
    // current user.
    @Query("SELECT * FROM User")
    fun loadUser(): Flow<List<User>>


}