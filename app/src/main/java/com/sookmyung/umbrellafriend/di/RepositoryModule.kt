package com.sookmyung.umbrellafriend.di

import com.sookmyung.umbrellafriend.data.repositoryImpl.HomeRepositoryImpl
import com.sookmyung.umbrellafriend.data.repositoryImpl.MypageRepositoryImpl
import com.sookmyung.umbrellafriend.data.repositoryImpl.UmbrellaRepositoryImpl
import com.sookmyung.umbrellafriend.data.repositoryImpl.UsersRepositoryImpl
import com.sookmyung.umbrellafriend.domain.repository.HomeRepository
import com.sookmyung.umbrellafriend.domain.repository.MypageRepository
import com.sookmyung.umbrellafriend.domain.repository.UmbrellaRepository
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

    @Binds
    @Singleton
    abstract fun bindToHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    @Singleton
    abstract fun bindToMypageRepository(
        mypageRepositoryImpl: MypageRepositoryImpl
    ): MypageRepository

    @Binds
    @Singleton
    abstract fun bindToUmbrellaRepository(
        umbrellaRepositoryImpl: UmbrellaRepositoryImpl
    ): UmbrellaRepository

}
