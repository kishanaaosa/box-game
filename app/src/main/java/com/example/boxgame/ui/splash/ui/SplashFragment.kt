package com.example.boxgame.ui.splash.ui

import android.annotation.SuppressLint
import androidx.activity.addCallback
import com.example.boxgame.MainActivity
import com.example.boxgame.R
import com.example.boxgame.base.FragmentBase
import com.example.boxgame.base.ToolbarModel
import com.example.boxgame.base.ViewModelBase
import com.example.boxgame.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : FragmentBase<ViewModelBase, FragmentSplashBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_splash

    override fun setupToolbar() {
        (activity as MainActivity).setStatusBarColor(R.color.black, false)
        viewModel.setToolbarItems(
            ToolbarModel(
                isVisible = false, title = ""
            )
        )
    }

    override fun getViewModelClass(): Class<ViewModelBase> = ViewModelBase::class.java

    override fun getViewModelIsSharedViewModel(): Boolean = false


    @SuppressLint("HardwareIds")
    override fun initializeScreenVariables() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {}

    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onResume() {
        super.onResume()
        //rotateLogo()
        GlobalScope.launch(context = Dispatchers.Main) {
            delay(2000)
            navigateToScreen()
        }
    }

    private fun rotateLogo() {
        //val animation = AnimationUtils.loadAnimation(context, R.anim.rotate)
        //getDataBinding().imgAnimSplash?.startAnimation(animation)
    }

    private fun navigateToScreen() {
        (activity as MainActivity).navigateToNextScreenThroughDirections(
            SplashFragmentDirections.actionSplashFragmentToHomeFragment()
        )
    }

}