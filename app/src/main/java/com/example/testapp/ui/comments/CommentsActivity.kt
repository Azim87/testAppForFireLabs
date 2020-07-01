package com.example.testapp.ui.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.model.Comments
import com.example.testapp.ui.comments.adapter.CommentsAdapter
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.item_alert.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentsActivity : AppCompatActivity() {
    private val TAG = "SecondActivity"
    private val mViewModel by viewModel<CommentsViewModel>()
    private lateinit var mAdapter: CommentsAdapter
    private val mCommentsList: ArrayList<Comments> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        title = "Comments"
        initRecycler()
        subscribeToViewModel()
    }

    private fun initRecycler() {
        comments_recycler.apply {
            mAdapter = CommentsAdapter(this@CommentsActivity::onCommentsClick)
            layoutManager = LinearLayoutManager(this@CommentsActivity)
            adapter = mAdapter
        }
    }

    private fun subscribeToViewModel() {
//        fetch comments data from api
        val postId = intent.getIntExtra("postId", 0)
        mViewModel.fetchComments(postId)
        mViewModel.commentsList.observe(this, Observer { commentsList ->
            mCommentsList.addAll(commentsList)
            mAdapter.setComments(mCommentsList)
        })

//       show loading process
        mViewModel.showLoading.observe(this, Observer { showIsLoading ->
            comments_progress.visibility = if (showIsLoading) View.VISIBLE else View.INVISIBLE
        })
    }

    private fun onCommentsClick(comments: Comments) {}

    private fun addCustomComments() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.item_alert, null)
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setIcon(R.drawable.ic_launcher_background)
        alertDialog.setView(mDialogView)
        alertDialog.setTitle("Добавить комментарий")
        val dialog: AlertDialog = alertDialog.create()

        mDialogView.add_button.setOnClickListener {
            mCommentsList.add(Comments(
                0,
                0,
                mDialogView.input_name.text.toString().trim(),
                mDialogView.input_email.text.toString().trim(),
                mDialogView.input_comments.text.toString().trim()
            ))
            if ( mDialogView.input_name.text.isEmpty() ||
                mDialogView.input_email.text.isEmpty() ||
                mDialogView.input_comments.text.isEmpty()) {
                mDialogView.input_name.error = "введите имя"
                mDialogView.input_email.error = "введите имя"
                mDialogView.input_comments.error = "введите имя"
            } else {
                mAdapter.setComments(mCommentsList)
                dialog.dismiss()
            }
        }
        dialog.show()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.comments_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_add) {
            addCustomComments()
        }
        return super.onOptionsItemSelected(item)
    }
}