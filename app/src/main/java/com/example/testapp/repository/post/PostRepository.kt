package com.example.testapp.repository.post

import com.example.testapp.model.Posts
import com.example.testapp.util.UseCaseResult

interface PostRepository {
   suspend fun getPosts() : UseCaseResult<List<Posts>>
}