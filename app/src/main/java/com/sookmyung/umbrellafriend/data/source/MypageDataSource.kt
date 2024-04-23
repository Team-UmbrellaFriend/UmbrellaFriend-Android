package com.sookmyung.umbrellafriend.data.source

import com.sookmyung.umbrellafriend.data.entity.request.LoginRequest
import com.sookmyung.umbrellafriend.data.entity.response.BaseResponse
import com.sookmyung.umbrellafriend.data.entity.response.MypageResponse
import com.sookmyung.umbrellafriend.data.entity.response.TokenResponse
import com.sookmyung.umbrellafriend.data.service.MypageService
import com.sookmyung.umbrellafriend.data.service.UsersService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class MypageDataSource @Inject constructor(
    private val mypageService: MypageService
) {
    suspend fun getMypage(): BaseResponse<MypageResponse> = mypageService.getMypage()
}