package com.damiankwasniak.interview.fragment


import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.damiankwasniak.interview.R
import com.damiankwasniak.interview.base.BaseFragment
import com.damiankwasniak.interview.base.BaseViewModel
import com.damiankwasniak.interview.databinding.FragmentCodeBinding
import com.damiankwasniak.interview.extensions.exhaustive
import com.damiankwasniak.interview.viewmodel.CodeFragmentViewModel
import kotlinx.android.synthetic.main.fragment_code.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CodeFragment :
    BaseFragment<CodeFragmentViewModel, CodeFragmentViewModel.Command, FragmentCodeBinding>() {

    private val viewModel: CodeFragmentViewModel by viewModel()

    override fun provideLayoutRes(): Int = R.layout.fragment_code

    override fun getViewModel(): BaseViewModel<CodeFragmentViewModel.Command> = viewModel

    override fun bindData(binding: FragmentCodeBinding) {
        binding.viewModel = viewModel
    }

    override fun onViewStateChanged(command: CodeFragmentViewModel.Command) {
        when (command) {
            CodeFragmentViewModel.Command.CodeCorrect -> onCodeCorrect()
        }.exhaustive
    }

    private fun onCodeCorrect() {
        val direction = CodeFragmentDirections.actionCodeFragmentToCameraFragment()
        findNavController().navigate(direction)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        codeInput.onCodeEnteredListener { code ->
            viewModel.onCodeEntered(code)
        }

        codeInput.onCodeChangedListener {
            viewModel.onCodeChanged()
        }
    }


}
