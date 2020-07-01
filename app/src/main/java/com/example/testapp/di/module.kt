package com.example.testapp.di

import com.example.testapp.api.ApiService
import com.example.testapp.repository.comments.CommentsRepository
import com.example.testapp.repository.comments.CommentsRepositoryImpl
import com.example.testapp.repository.post.PostRepository
import com.example.testapp.repository.post.PostRepositoryImpl
import com.example.testapp.ui.main.MainViewModel
import com.example.testapp.ui.comments.CommentsViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel{MainViewModel(get())}
    viewModel { CommentsViewModel(get()) }
}

val repoModule = module {
    single<PostRepository> { PostRepositoryImpl(get()) }
    single<CommentsRepository> { CommentsRepositoryImpl(get()) }
}

val retrofitModule = module {

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(client: OkHttpClient) : ApiService {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
    single { provideHttpClient() }
    single { provideRetrofit(get()) }
}

val appModules = listOf(viewModelModule, repoModule, retrofitModule)