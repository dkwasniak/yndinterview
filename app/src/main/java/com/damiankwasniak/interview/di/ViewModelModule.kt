package com.damiankwasniak.interview.di

import com.damiankwasniak.interview.ui.camera.CameraFragmentViewModel
import com.damiankwasniak.interview.ui.code.CodeFragmentViewModel
import com.damiankwasniak.interview.ui.gallery.GalleryFragmentViewModel
import com.damiankwasniak.interview.ui.main.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel { MainActivityViewModel(get(), get()) }

    viewModel { CodeFragmentViewModel(get(), get()) }

    viewModel { CameraFragmentViewModel(get(), get()) }

    viewModel { GalleryFragmentViewModel(get(), get()) }

}