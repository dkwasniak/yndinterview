package com.damiankwasniak.interview.fragment


import com.damiankwasniak.interview.R
import com.damiankwasniak.interview.base.BaseFragment
import com.damiankwasniak.interview.base.BaseViewModel
import com.damiankwasniak.interview.databinding.FragmentGalleryBinding
import com.damiankwasniak.interview.viewmodel.GalleryFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryFragment :
    BaseFragment<GalleryFragmentViewModel, GalleryFragmentViewModel.Command, FragmentGalleryBinding>() {

    private val viewModel: GalleryFragmentViewModel by viewModel()

    override fun provideLayoutRes(): Int = R.layout.fragment_gallery

    override fun getViewModel(): BaseViewModel<GalleryFragmentViewModel.Command> = viewModel

    override fun bindData(binding: FragmentGalleryBinding) {

    }

    override fun onViewStateChanged(command: GalleryFragmentViewModel.Command) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
