package com.example.testapp.repository.comments

import com.example.testapp.model.Comments
import com.example.testapp.util.UseCaseResult

interface CommentsRepository {
   suspend fun getComments(postId: Int) : UseCaseResult<List<Comments>>
}