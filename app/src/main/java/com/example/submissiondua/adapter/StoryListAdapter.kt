package com.example.submissiondua.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import androidx.core.util.Pair
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.submissiondua.databinding.ItemMystoryBinding
import com.example.submissiondua.ui.detail.StoryDetailActivity
import com.example.submissiondua.data.response.ListStoryItem

class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.StoryViewHolder>(ITEM_COMPARATOR) {

    class StoryViewHolder(private val binding: ItemMystoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(storyItem: ListStoryItem) {
            Glide.with(itemView.context)
                .load(storyItem.photoUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgItemPhoto)

            binding.tvItemName.text = storyItem.name

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, StoryDetailActivity::class.java).apply {
                    putExtra(StoryDetailActivity.ID, storyItem.id)
                }
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context as Activity,
                    Pair(binding.imgItemPhoto, "profile_image"),
                    Pair(binding.tvItemName, "name")
                )
                context.startActivity(intent, options.toBundle())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemMystoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val storyItem = getItem(position)
        if (storyItem != null) {
            holder.bind(storyItem)
        }
    }

    companion object {
        val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}