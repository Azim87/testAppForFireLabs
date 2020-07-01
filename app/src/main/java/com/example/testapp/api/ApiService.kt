package com.example.testapp.api

import com.example.testapp.model.Comments
import com.example.testapp.model.Posts
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("posts")
    fun getPostsAsync(
        @Query("_limit") limit: Int = 30
    ): Deferred<List<Posts>>

    @GET("comments")
    fun getCommentsAsync(
        @Query("postId") postId: Int
    ): Deferred<List<Comments>>
}