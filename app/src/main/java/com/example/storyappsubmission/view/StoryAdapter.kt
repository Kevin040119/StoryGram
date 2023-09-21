package com.example.storyappsubmission.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyappsubmission.databinding.ListStoryBinding
import com.example.storyappsubmission.response.ListStoryItem

class StoryAdapter : ListAdapter<ListStoryItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding : ListStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story : ListStoryItem) {
            binding.userName.text = story.name.toString()
            binding.storyDescription.text = story.description.toString()

            Glide.with(binding.root)
                .load(story.photoUrl)
                .into(binding.imgStory)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, StoryDetail::class.java)
            intent.putExtra("description", story.description)
            intent.putExtra("name", story.name)
            intent.putExtra("photo", story.photoUrl)
            holder.itemView.context.startActivity(intent)
        }
    }
}