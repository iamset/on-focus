package com.focusg.focusgroup.domain.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

data class User(
    @PrimaryKey val username: String="",
    @ColumnInfo var uid: String="",
    @ColumnInfo val photoUrl: String?=null,
    @ColumnInfo val email: String? = null,
    @ColumnInfo val createdAt: Long = 0L,
    @ColumnInfo val updatedAt: Long = 0L
)
