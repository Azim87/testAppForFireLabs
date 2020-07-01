package com.example.testapp.ui.comments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.model.Comments
import com.example.testapp.model.Posts

class CommentsAdapter(private val function: (Comments) -> Unit) :
    RecyclerView.Adapter<CommentsAdapter.MainViewHolder>() {
    private val mCommentsList: ArrayList<Comments> = ArrayList()

    fun setComments(commentsList: ArrayList<Comments> = ArrayList()) {
        mCommentsList.clear()
        mCommentsList.addAll(commentsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return MainViewHolder(view, function)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindPosts(mCommentsList[position])
    }

    override fun getItemCount(): Int = mCommentsList.size


    inner class MainViewHolder(itemView: View, val function: (Comments) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val text: TextView = itemView.findViewById(R.id.post_title)
        fun bindPosts(comments: Comments) {
            text.text = comments.name

            itemView.setOnClickListener {
                function(comments)
            }
        }
    }
}






