package com.sookmyung.umbrellafriend.di

import com.sookmyung.umbrellafriend.data.service.HomeService
import com.sookmyung.umbrellafriend.data.service.MypageService
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
    @Provides
    fun providesHomeService(@RetrofitModule.UmbrellaFriendType retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    @Provides
    fun providesMypageService(@RetrofitModule.UmbrellaFriendType retrofit: Retrofit): MypageService =
        retrofit.create(MypageService::class.java)

}
