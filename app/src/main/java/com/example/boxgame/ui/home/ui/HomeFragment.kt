package com.example.boxgame.ui.home.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.example.boxgame.BuildConfig
import com.example.boxgame.MainActivity
import com.example.boxgame.R
import com.example.boxgame.base.FragmentBase
import com.example.boxgame.base.ToolbarModel
import com.example.boxgame.caller.CallerInfoService
import com.example.boxgame.databinding.FragmentHomeBinding
import com.example.boxgame.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : FragmentBase<HomeViewModel, FragmentHomeBinding>() {

    override fun getLayoutId() = R.layout.fragment_home

    override fun setupToolbar() {
        (activity as MainActivity).setStatusBarColor(R.color.black, false)
        viewModel.setToolbarItems(
            ToolbarModel(
                isVisible = true, title = "Home Screen"
            )
        )
    }

    override fun initializeScreenVariables() {
        getDataBinding().viewModel = viewModel
        viewModel.initVariables()
        setUpObserver()
        setUpCaller()
        startForegroundService()
    }

    private fun setUpCaller() {
        if (!Settings.canDrawOverlays(requireContext())) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:${BuildConfig.APPLICATION_ID}")
            )
            startActivityForResult(intent, 200)
        }
    }

    private fun startForegroundService() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireContext().startForegroundService(Intent(context, CallerInfoService::class.java))
        } else {
            requireContext().startService(Intent(context, CallerInfoService::class.java))
        }

      /*  val intent = Intent(requireContext(), CallerInfoService::class.java)
        ContextCompat.startForegroundService(requireContext(), intent)*/
    }

    private fun setUpObserver() {
        viewModel.onNextClick.observe(viewLifecycleOwner) {
            (activity as MainActivity).navigateToNextScreenThroughDirections(HomeFragmentDirections.actionHomeFragmentToGameFragment())
        }
    }

    override fun getViewModelClass() = HomeViewModel::class.java

    override fun getViewModelIsSharedViewModel() = false

}