package com.example.boxgame

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.boxgame.base.ToolbarModel
import com.example.boxgame.base.ViewModelBase
import com.example.boxgame.databinding.ActivityMainBinding
import com.example.boxgame.utils.DebugLog
import com.example.boxgame.utils.HomeToolbarClickHandler
import com.example.boxgame.utils.Utils
import com.example.boxgame.utils.sharedpref.MyPreference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), HomeToolbarClickHandler {

    private var dialog: Dialog? = null
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModelBase
    lateinit var navHostFragment: NavHostFragment

    @Inject
    lateinit var mPref: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DebugLog.d("onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        init()
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
    }

    fun init() {
        binding.layToolbar.clickListener = this
        viewModel = ViewModelProvider(this)[ViewModelBase::class.java]
        setToolbarClickListener(this)
    }

    fun setToolbarClickListener(clickListener: Any) {
        binding.layToolbar.clickListener = clickListener as HomeToolbarClickHandler?
    }

    /**
     * This method is used for Navigating from One Screen to Next Screen using Navigation
     * Direction graph.
     * @param navigationId This is the direction Id from Navigation graph
     */
    fun navigateToNextScreenThroughDirections(navigationId: NavDirections) {
        try {
            navHostFragment.findNavController().navigate(navigationId)
        } catch (e: Exception) {
            DebugLog.print(e)
        }
    }

    /**
     * Show Progress dialog
     * @param t: true show progress bar
     *
     *  */
    fun displayProgress(t: Boolean) {
        // binding.loading = t
        if (t) {
            if (dialog == null) {
                dialog = Utils.progressDialog(this)
            }
            dialog?.show()
        } else {
            dialog?.dismiss()
        }
    }

    /**
     * This method is used to hide the keyboard.
     */
    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * Toolbar manages items and visibility according to
     */
    fun toolBarManagement(toolbarModel: ToolbarModel?) {
        if (toolbarModel != null) {
            binding.layToolbar.toolbarModel = toolbarModel
        }
    }

    fun setStatusBarColor(color: Int, isLightStatusBar: Boolean) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, color)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = isLightStatusBar
        }
    }

    override fun onBackClick() {
        onBackPressedDispatcher.onBackPressed()
    }
}