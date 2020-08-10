package com.damiankwasniak.interview.viewmodel

import com.damiankwasniak.interview.base.BaseViewModel
import com.damiankwasniak.interview.provider.ResourcesProvider


class GalleryFragmentViewModel(
    resourcesProvider: ResourcesProvider
) : BaseViewModel<GalleryFragmentViewModel.Command>(resourcesProvider) {


    sealed class Command {

    }

}
