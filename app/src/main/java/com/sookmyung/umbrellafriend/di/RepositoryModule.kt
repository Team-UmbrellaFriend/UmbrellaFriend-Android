package com.sookmyung.umbrellafriend.di

import com.sookmyung.umbrellafriend.data.repositoryImpl.UsersRepositoryImpl
import com.sookmyung.umbrellafriend.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindToUsersRepository(
        usersRepositoryImpl: UsersRepositoryImpl
    ): UsersRepository
}
