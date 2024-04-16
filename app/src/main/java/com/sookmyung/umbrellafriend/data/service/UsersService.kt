package com.sookmyung.umbrellafriend.data.service

import com.sookmyung.umbrellafriend.data.entity.response.BaseResponse
import com.sookmyung.umbrellafriend.data.entity.response.TokenResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface UsersService {
    @Multipart
    @POST("users/signup/")
    suspend fun postJoin(
        @Part studentCard:MultipartBody.Part,
        @PartMap body: HashMap<String, RequestBody>
    ): BaseResponse<TokenResponse>
}