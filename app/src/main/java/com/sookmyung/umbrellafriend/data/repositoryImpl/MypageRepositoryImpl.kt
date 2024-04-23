package com.sookmyung.umbrellafriend.data.repositoryImpl

import com.sookmyung.umbrellafriend.data.source.MypageDataSource
import com.sookmyung.umbrellafriend.domain.entity.Mypage
import com.sookmyung.umbrellafriend.domain.repository.MypageRepository
import javax.inject.Inject

class MypageRepositoryImpl @Inject constructor(
    private val mypageDataSource: MypageDataSource
) : MypageRepository {
    override suspend fun getMypage(): Result<Mypage> = runCatching {
        mypageDataSource.getMypage()
    }.mapCatching { response -> requireNotNull(response.data).toMypage() }
}