package com.example.testapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.model.Posts
import com.example.testapp.repository.post.PostRepository
import com.example.testapp.util.SingleLiveEvent
import com.example.testapp.util.UseCaseResult
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val mainRepository: PostRepository) : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val postsList = MutableLiveData<List<Posts>>()
    val showError = SingleLiveEvent<String>()


    fun fetchPosts() {
        showLoading.value = true
        launch {
            val result = withContext(Dispatchers.IO) {
                mainRepository.getPosts()
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> postsList.value = result.data
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