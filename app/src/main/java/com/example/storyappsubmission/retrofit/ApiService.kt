package com.example.storyappsubmission.retrofit

import com.example.storyappsubmission.response.ErrorResponse
import com.example.storyappsubmission.response.LoginResponse
import com.example.storyappsubmission.response.RegisterResponse
import com.example.storyappsubmission.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @FormUrlEncoded
    @POST("v1/register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("v1/login")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<LoginResponse>

    @GET("v1/stories")
    fun getStories(
        @Header("Authorization") token : String
    ) : Call<StoryResponse>

    @Multipart
    @POST("v1/stories")
    fun uploadImage(
        @Header("Authorization") token : String,
        @Part("description") description : RequestBody,
        @Part file : MultipartBody.Part
    ) : Call<ErrorResponse>

}

//@Headers("Authorization: token <Personal Access Token>")