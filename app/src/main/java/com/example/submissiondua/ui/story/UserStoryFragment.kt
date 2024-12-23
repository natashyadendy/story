package com.example.submissiondua.ui.story

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissiondua.R
import com.example.submissiondua.adapter.StoryListAdapter
import com.example.submissiondua.databinding.FragmentMystoryBinding
import com.example.submissiondua.ui.FactoryViewModel
import com.example.submissiondua.ui.add.AddStoryFragment

class UserStoryFragment : Fragment() {

    private var _binding: FragmentMystoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserStoryViewModel by viewModels {
        FactoryViewModel.getInstance(requireContext())
    }
    private val storyAdapter by lazy { StoryListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMystoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        bindViewModel()

        binding.btnAddStory.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            val addStoryFragment = AddStoryFragment()
            fragmentTransaction.replace(R.id.fragment_activity, addStoryFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {
        binding.rvStories.apply {
            layoutManager = LinearLayoutManager(requireContext()).also { manager ->
                addItemDecoration(DividerItemDecoration(context, manager.orientation))
            }
            adapter = storyAdapter
        }
    }

    private fun bindViewModel() {
        viewModel.story.observe(viewLifecycleOwner) { pagingData ->
            storyAdapter.submitData(lifecycle, pagingData)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }
    }
}