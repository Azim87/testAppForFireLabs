package com.example.testapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.model.Posts
import com.example.testapp.ui.main.adapter.MainAdapter
import com.example.testapp.ui.comments.CommentsActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
//    private val TAG = "MainActivity"
    private lateinit var mAdapter: MainAdapter
    private val mViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Posts"
        initRecycler()
        subscribeToViewModel()
    }

    private fun initRecycler() {
        mAdapter = MainAdapter(this@MainActivity::onClickItem)
        main_recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
        }
    }

    private fun subscribeToViewModel() {
//        fetch data from api
        mViewModel.postsList.observe(this, Observer { postLists ->
            mAdapter.setPosts(postLists as ArrayList<Posts>)
        })

//        show loading process
        mViewModel.showLoading.observe(this, Observer { showIsLoading ->
            progress_bar.visibility = if (showIsLoading) View.VISIBLE else View.INVISIBLE
        })
        mViewModel.fetchPosts()
    }

    private fun onClickItem(posts: Posts) {
        val intent = Intent(this, CommentsActivity::class.java)
        intent.putExtra("postId", posts.id)
        startActivity(intent)
        Toast.makeText(this, "post Id: " + posts.id , Toast.LENGTH_SHORT).show()
    }
}