package com.example.submissiondua.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.submissiondua.databinding.DetailPageBinding
import com.example.submissiondua.ui.FactoryViewModel

class StoryDetailActivity : AppCompatActivity() {

    private val viewModel by viewModels<DetailStoryViewModel> {
        FactoryViewModel.getInstance(this)
    }
    private lateinit var binding: DetailPageBinding

    private lateinit var storyId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storyId = intent.getStringExtra(ID) ?: run {
            finish()
            return
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.apply {
            isLoading.observe(this@StoryDetailActivity) { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }

            getUser().observe(this@StoryDetailActivity) { user ->
                user?.takeIf { it.token.isNotEmpty() }?.let { viewModel.getDetail(it.token, storyId) }
            }

            detailName.observe(this@StoryDetailActivity) { name ->
                binding.tvTitle.text = name
            }

            detailDesc.observe(this@StoryDetailActivity) { description ->
                binding.tvDesc.text = description
            }

            detailPhotoUrl.observe(this@StoryDetailActivity) { photoUrl ->
                Glide.with(this@StoryDetailActivity)
                    .load(photoUrl)
                    .into(binding.imgItemPhoto)
            }
        }
    }

    companion object {
        const val ID = "id"
    }
}
