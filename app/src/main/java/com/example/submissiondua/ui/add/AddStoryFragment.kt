package com.example.submissiondua.ui.add

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.submissiondua.R
import com.example.submissiondua.databinding.FragmentUploadBinding
import com.example.submissiondua.pref.compressImageFile
import com.example.submissiondua.pref.copyUriToFile
import com.example.submissiondua.ui.FactoryViewModel
import com.example.submissiondua.ui.camera.CameraActivity
import com.example.submissiondua.ui.story.UserStoryFragment
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddStoryFragment : Fragment() {

    private lateinit var binding: FragmentUploadBinding
    private var selectedImageUri: Uri? = null

    private val uploadViewModel by viewModels<AddStoryViewModel> {
        FactoryViewModel.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeObservers()
        initializeListeners()
    }

    private fun initializeObservers() {
        uploadViewModel.apply {
            isLoading.observe(viewLifecycleOwner) { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }

            statusMessage.observe(viewLifecycleOwner) { message ->
                message?.let {
                    showToast(it)
                    uploadViewModel.clearStatusMessage()
                }
            }

            isUploadSuccess.observe(viewLifecycleOwner) { isUploadSuccess ->
                if (isUploadSuccess) navigateToStoryList()
            }
        }
    }

    private fun initializeListeners() {
        with(binding) {
            btGallery.setOnClickListener { openGallery() }
            btCamera.setOnClickListener { openCamera() }
            btUpload.setOnClickListener { uploadStoryContent() }
        }
    }

    private fun openGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            showSelectedImage()
        } ?: showToast("No Image Found")
    }

    private fun showSelectedImage() {
        selectedImageUri?.let { uri ->
            binding.previewImageView.setImageURI(uri)
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(requireContext(), CameraActivity::class.java)
        launcherIntentCameraX.launch(cameraIntent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == CameraActivity.CAMERAX_RESULT) {
            selectedImageUri = result.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showSelectedImage()
        }
    }

    private fun uploadStoryContent() {
        val description = binding.etUploadDescription.text.toString()

        when {
            selectedImageUri == null -> showToast("Add photo")
            description.isBlank() -> showToast("Add description")
            else -> selectedImageUri?.let { uri ->
                val imageFile = copyUriToFile(uri, requireContext()).compressImageFile()
                val descriptionBody = description.toRequestBody(getString(R.string.text_plain).toMediaType())
                val imageBody = imageFile.asRequestBody(getString(R.string.image_jpeg).toMediaType())
                val multipartBody = MultipartBody.Part.createFormData("photo", imageFile.name, imageBody)

                uploadViewModel.getUser().observe(viewLifecycleOwner) { user ->
                    user.token?.let { token ->
                        uploadViewModel.uploadStoryContent(token, multipartBody, descriptionBody)
                    }
                }
            }
        }
    }

    private fun navigateToStoryList() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_activity, UserStoryFragment())
            .commit()
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
