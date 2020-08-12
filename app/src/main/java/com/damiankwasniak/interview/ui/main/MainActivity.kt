package com.damiankwasniak.interview.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.damiankwasniak.interview.R
import com.damiankwasniak.interview.ui.base.BaseActivity
import com.damiankwasniak.interview.ui.base.BaseViewModel
import com.damiankwasniak.interview.extensions.exhaustive
import com.damiankwasniak.interview.permission.PermissionsManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainActivityViewModel, MainActivityViewModel.Command>() {

    private val viewModel: MainActivityViewModel by viewModel()

    private val permissionsManager: PermissionsManager by inject()

    lateinit var navController: NavController

    private lateinit var navFragment: NavHostFragment

    override fun provideLayoutRes(): Int = R.layout.activity_main

    override fun getViewModel(): BaseViewModel<MainActivityViewModel.Command> = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController(R.id.nav_host_fragment)
        navFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    override fun onViewStateChanged(command: MainActivityViewModel.Command) {
        when (command) {
            MainActivityViewModel.Command.AppResumed -> navigateToCodeView()
        }.exhaustive
    }

    private fun navigateToCodeView() {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
