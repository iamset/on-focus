package com.focusg.focusgroup.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.focusg.focusgroup.data.database.AppDatabase
import com.focusg.focusgroup.data.database.dao.UserDao
import com.focusg.focusgroup.domain.service.AuthService
import com.focusg.focusgroup.data.repository.AuthRepositoryImpl
import com.focusg.focusgroup.data.repository.UsersRepositoryImpl
import com.focusg.focusgroup.di.util.DATABASE_NAME
import com.focusg.focusgroup.domain.repository.AuthRepository
import com.focusg.focusgroup.domain.repository.UsersRepository
import com.focusg.focusgroup.domain.service.CacheService
import com.focusg.focusgroup.domain.service.UserService
import com.focusg.focusgroup.firebase.FirebaseUsersService
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authService: AuthService,
    ): AuthRepository{
        return AuthRepositoryImpl(authService)
    }

    @Provides
    @Singleton
    fun provideUsersRepository(
        userService: UserService,
        cacheService: CacheService
    ): UsersRepository{
        return UsersRepositoryImpl(userService, cacheService)
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room.databaseBuilder(
            context, AppDatabase::class.java, DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao{
        return database.userDao()
    }

}