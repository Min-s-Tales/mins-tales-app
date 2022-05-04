package com.example.minstalesapp.Api

import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.Model.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @GET("api/story")
    suspend fun getStory(): Response<MutableList<Story>>

    @GET("api/story/{tag}")
    suspend fun getStoryByTags(@Query("tag") tag: String): Response<MutableList<Story>>

    @POST("api/login")
    suspend fun login(@Body user: User): Response<User>

    @GET("api/user/{user}")
    suspend fun getUser(): Response<User>

/*    @GET("posts/{num}")
    suspend fun getPostById(@Path("num") num : Int): Response<Post>


    @GET("posts/{num}")
    suspend fun getPostById(@Path("num") num : Int): Response<Post>


    @GET("comments")
    suspend fun getCommentsByPost(@Query("postId") postId : Int): Response<MutableList<Comment>>
*/
  /*  @POST("posts")
    suspend fun createPost(@Body post: Post): Response<Post>  */
}