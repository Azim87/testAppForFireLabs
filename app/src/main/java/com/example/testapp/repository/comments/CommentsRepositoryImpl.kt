package com.example.testapp.repository.comments

import com.example.testapp.api.ApiService
import com.example.testapp.model.Comments
import com.example.testapp.util.UseCaseResult

class CommentsRepositoryImpl(private val commentsApi: ApiService) : CommentsRepository {
    override suspend fun getComments(postId: Int): UseCaseResult<List<Comments>> {
        return try {
            val postsResult = commentsApi.getCommentsAsync(postId).await()
            UseCaseResult.Success(postsResult)

        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }
}