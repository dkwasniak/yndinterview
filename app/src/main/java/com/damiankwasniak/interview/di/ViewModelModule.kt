package com.damiankwasniak.interview.di

import com.damiankwasniak.interview.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel { MainActivityViewModel(get(), get()) }

    viewModel { CodeFragmentViewModel(get(), get()) }

    viewModel { CameraFragmentViewModel(get(), get(), get()) }

    viewModel { GalleryFragmentViewModel(get()) }

}