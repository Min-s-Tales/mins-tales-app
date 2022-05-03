package com.example.minstalesapp.Api

import com.example.minstalesapp.Model.Story
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("/api/story")
    suspend fun getStory(): Response<MutableList<Story>>

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