package com.damiankwasniak.interview.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.damiankwasniak.interview.extensions.exhaustive


abstract class BaseFragment<T : BaseViewModel<U>, U : Any, B : ViewDataBinding> : Fragment(),
    OnBackPressedAwareFragment {

    lateinit var binding: B

    abstract fun provideLayoutRes(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<B>(
            inflater, provideLayoutRes(), container, false
        )
        bindData(binding)
        return binding.root
    }

    abstract fun bindData(binding: B)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerViewStateObserver()
        registerBaseCommandsObserver()
    }

    private fun registerViewStateObserver() {
        getViewModel().observeViewState {
            onViewStateChanged(it)
        }
    }

    private fun registerBaseCommandsObserver() {
        getViewModel().observeBaseViewState {
            onBaseViewStateChanged(it)
        }
    }

    open fun onBaseViewStateChanged(command: BaseViewModel.BaseViewCommand) {
        when (command) {
            else -> {
            }
        }.exhaustive
    }


    abstract fun getViewModel(): BaseViewModel<U>

    abstract fun onViewStateChanged(command: U)

    override fun onResume() {
        super.onResume()
        getViewModel().onResume()
    }

    override fun onDetach() {
        super.onDetach()
        getViewModel().dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    open fun close() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }
}

interface OnBackPressedAwareFragment {
    fun onBackPressed(): Boolean
}