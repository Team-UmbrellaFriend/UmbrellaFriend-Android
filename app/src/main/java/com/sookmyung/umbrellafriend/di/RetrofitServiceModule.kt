package com.sookmyung.umbrellafriend.di

import com.sookmyung.umbrellafriend.data.service.UsersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RetrofitServiceModule {

    @Provides
    fun providesUsersService(@RetrofitModule.UmbrellaFriendType retrofit: Retrofit): UsersService =
        retrofit.create(UsersService::class.java)

}
