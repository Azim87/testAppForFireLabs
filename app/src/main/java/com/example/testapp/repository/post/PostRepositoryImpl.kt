package com.example.testapp.repository.post

import com.example.testapp.api.ApiService
import com.example.testapp.model.Posts
import com.example.testapp.repository.post.PostRepository
import com.example.testapp.util.UseCaseResult

class PostRepositoryImpl(private val api: ApiService) :
    PostRepository {
    override suspend fun getPosts(): UseCaseResult<List<Posts>> {

        return try {
            val postsResult = api.getPostsAsync().await()
            UseCaseResult.Success(postsResult)
        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }
}