package com.sookmyung.umbrellafriend.data.service

import com.sookmyung.umbrellafriend.data.entity.request.EditRequest
import com.sookmyung.umbrellafriend.data.entity.request.LoginRequest
import com.sookmyung.umbrellafriend.data.entity.request.WithdrawRequest
import com.sookmyung.umbrellafriend.data.entity.response.BaseResponse
import com.sookmyung.umbrellafriend.data.entity.response.MypageProfileResponse
import com.sookmyung.umbrellafriend.data.entity.response.TokenResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface UsersService {
    @Multipart
    @POST("users/signup/")
    suspend fun postJoin(
        @Part studentCard: MultipartBody.Part,
        @PartMap body: HashMap<String, RequestBody>
    ): BaseResponse<TokenResponse>

    @POST("users/login/")
    suspend fun postLogin(
        @Body loginRequest: LoginRequest
    ): BaseResponse<TokenResponse>

    @GET("users/logout/")
    suspend fun getLogout(): BaseResponse<Unit>

    @GET("/users/profile/{user_id}/")
    suspend fun getUserProfile(
        @Path("user_id") userId: Int
    ): BaseResponse<MypageProfileResponse>

    @PUT("/users/profile/{user_id}/")
    suspend fun putUserProfile(
        @Path("user_id") userId: Int,
        @Body editRequest: EditRequest
    ): BaseResponse<MypageProfileResponse>

    @HTTP(method = "DELETE", path = "/users/withdrawal/", hasBody = true)
    suspend fun deleteWithdraw(
        @Body withdrawRequest: WithdrawRequest
    ): BaseResponse<String>
}