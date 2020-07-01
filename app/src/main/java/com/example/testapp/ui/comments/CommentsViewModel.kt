package com.example.testapp.ui.comments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.model.Comments
import com.example.testapp.repository.comments.CommentsRepository
import com.example.testapp.util.SingleLiveEvent
import com.example.testapp.util.UseCaseResult
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CommentsViewModel(private val commentsRepository: CommentsRepository) : ViewModel(),
    CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val commentsList = MutableLiveData<List<Comments>>()
    val showError = SingleLiveEvent<String>()

    fun fetchComments(postId: Int) {
        showLoading.value = true
        launch {
            val result = withContext(Dispatchers.IO) {
                commentsRepository.getComments(postId)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> commentsList.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
        job.complete()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}