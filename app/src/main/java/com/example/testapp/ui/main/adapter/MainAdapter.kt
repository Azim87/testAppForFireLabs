package com.example.testapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.model.Comments
import com.example.testapp.model.Posts

class MainAdapter(private val function: (Posts) -> Unit) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private val mPostList: ArrayList<Posts> = ArrayList()

    fun setPosts(postList: ArrayList<Posts> = ArrayList()) {
        mPostList.clear()
        mPostList.addAll(postList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return MainViewHolder(view, function)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindPosts(mPostList[position])
    }

    override fun getItemCount(): Int = mPostList.size


    inner class MainViewHolder(itemView: View, val function: (Posts) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val text: TextView = itemView.findViewById(R.id.post_title)
        fun bindPosts(post: Posts) {
            text.text = post.title

            itemView.setOnClickListener {
                function(post)
            }
        }
    }
}






