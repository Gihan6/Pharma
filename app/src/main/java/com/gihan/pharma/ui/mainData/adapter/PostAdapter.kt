package com.gihan.pharma.ui.mainData.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gihan.pharma.R
import com.gihan.pharma.model.Post
import kotlinx.android.synthetic.main.singlr_item_post.view.*

class PostAdapter(private val posts: ArrayList<Post>, val action: ListenerAdapter) :
    RecyclerView.Adapter<PostAdapter.DataViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.singlr_item_post, parent, false)
        )
    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(posts[position])
        holder.itemView.iv_delete.setOnClickListener(View.OnClickListener {
            action.delete(posts[position])
        })
    }

    fun addPost(posts: List<Post>) {
        this.posts.apply {
            clear()
            addAll(posts)
        }

    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(post: Post) {
            itemView.apply {
                tv_title.text = post.title
                tv_description.text = post.description


            }
        }
    }

}