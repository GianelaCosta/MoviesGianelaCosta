package com.example.movies.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.base.BaseViewHolder
import com.example.movies.data.model.Review
import kotlinx.android.synthetic.main.reviews_item.view.*

class ReviewAdapter (private val context: Context, private val reviewsList: List<Review>):
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return ReviewViewHolder(
            LayoutInflater.from(context).inflate(R.layout.reviews_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ReviewViewHolder -> holder.bind(reviewsList[position], position)
        }
    }

    inner class ReviewViewHolder(itemView: View) : BaseViewHolder<Review>(itemView) {
        override fun bind(item: Review, position: Int) {
            itemView.txt_reviewer_avatar.text = item.reviewer[0].toString()
            itemView.txt_reviewer_name.text = item.reviewer
            itemView.txt_review.text = item.reviewContent
        }
    }
}