package com.damiankwasniak.interview.ui.gallery


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.damiankwasniak.interview.R
import com.damiankwasniak.interview.databinding.FragmentGalleryBinding
import com.damiankwasniak.interview.ui.base.BaseFragment
import com.damiankwasniak.interview.ui.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_gallery.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryFragment :
    BaseFragment<GalleryFragmentViewModel, GalleryFragmentViewModel.Command, FragmentGalleryBinding>() {

    private val viewModel: GalleryFragmentViewModel by viewModel()

    override fun provideLayoutRes(): Int = R.layout.fragment_gallery

    override fun getViewModel(): BaseViewModel<GalleryFragmentViewModel.Command> = viewModel

    override fun bindData(binding: FragmentGalleryBinding) {
        binding.viewModel = viewModel
    }

    override fun onViewStateChanged(command: GalleryFragmentViewModel.Command) {
        // no op
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
