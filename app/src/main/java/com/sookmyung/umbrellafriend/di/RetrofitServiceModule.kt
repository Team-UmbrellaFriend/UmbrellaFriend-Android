package com.sookmyung.umbrellafriend.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RetrofitServiceModule {

//    @Provides
//    fun providesSampleService(@RetrofitModule.UmbrellaFriendType retrofit: Retrofit): SampleService =
//        retrofit.create(SampleService::class.java)
}
