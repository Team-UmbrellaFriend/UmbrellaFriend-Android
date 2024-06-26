package com.sookmyung.umbrellafriend.data.source

import com.sookmyung.umbrellafriend.data.entity.request.EditRequest
import com.sookmyung.umbrellafriend.data.entity.request.LoginRequest
import com.sookmyung.umbrellafriend.data.entity.request.WithdrawRequest
import com.sookmyung.umbrellafriend.data.entity.response.BaseResponse
import com.sookmyung.umbrellafriend.data.entity.response.MypageProfileResponse
import com.sookmyung.umbrellafriend.data.entity.response.TokenResponse
import com.sookmyung.umbrellafriend.data.service.UsersService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UsersDataSource @Inject constructor(
    private val usersService: UsersService
) {
    suspend fun postJoin(
        studentCard: MultipartBody.Part,
        body: HashMap<String, RequestBody>
    ): BaseResponse<TokenResponse> = usersService.postJoin(studentCard, body)

    suspend fun postLogin(
        loginRequest: LoginRequest
    ): BaseResponse<TokenResponse> = usersService.postLogin(loginRequest)

    suspend fun getLogout(): BaseResponse<Unit> = usersService.getLogout()

    suspend fun getUserProfile(userId: Int): BaseResponse<MypageProfileResponse> =
        usersService.getUserProfile(userId)

    suspend fun putUserProfile(
        userId: Int,
        editRequest: EditRequest
    ): BaseResponse<MypageProfileResponse> =
        usersService.putUserProfile(userId, editRequest)

    suspend fun deleteWithdraw(withdrawRequest: WithdrawRequest): BaseResponse<String> =
        usersService.deleteWithdraw(withdrawRequest)
}