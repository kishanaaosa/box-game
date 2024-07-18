package com.example.boxgame.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.boxgame.MainActivity
import com.example.boxgame.utils.sharedpref.MyPreference
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


/**
 * Abstract base class for Fragments
 */
abstract class FragmentBase<V : ViewModelBase, DataBinding : ViewDataBinding> :
    Fragment() {

    private lateinit var dataBinding: DataBinding
    private lateinit var mViewModel: V
    val viewModel: V get() = mViewModel

    @Inject
    lateinit var mPref: MyPreference

    /**
     * This is the abstract method by which we are generating the
     * Data binding with View from Single Screen.
     *
     */
    abstract fun getLayoutId(): Int

    /**
     * This is the generic method where we have to setup the toolbar from single place and
     * from all other extended fragment should have to manage the logic related to the toolbar
     * from this method
     */
    abstract fun setupToolbar()

    /**
     * This is the method from where we are initialized the
     * fragment specific data members and methods.
     */
    abstract fun initializeScreenVariables()

    protected abstract fun getViewModelClass(): Class<V>
    protected abstract fun getViewModelIsSharedViewModel(): Boolean

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.executePendingBindings()
        return dataBinding.root
    }


    fun getDataBinding() = dataBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeScreenVariables()
        observeToolbar()
        setupToolbar()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mViewModel = if (getViewModelIsSharedViewModel()) {
            ViewModelProvider(requireActivity())[getViewModelClass()]
        } else {
            ViewModelProvider(this)[getViewModelClass()]
        }
        setUpProgressBar()
        setUpSnackBar()
        setUpHideKeyBoard()
    }

    /**
     * This is generic method based on the MutableLive Data Concept where value changed with toolbar setup
     * will reflect and decide if value is true then it will display.
     */
    private fun observeToolbar() {
        viewModel.toolBarModel.observe(viewLifecycleOwner) {
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).toolBarManagement(it)
            }
        }
    }

    /**
     * This is generic method based on the MutableLive Data Concept where value changed with Progressbar
     * will reflect and decide if value is true then it will display.
     */
    private fun setUpProgressBar() {
        viewModel.progressBarDisplay.observe(this) { o: Boolean ->
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).displayProgress(o)
            }
        }
    }

    /**
     * This is generic method based on the MutableLive Data Concept where value changed with Snake bar
     * will reflect and display the message with Snake bar.
     */
    private fun setUpSnackBar() {
        viewModel.snackbarMessage.observe(this) { o: Any ->
            if (o is Int) {
                (activity as MainActivity).resources.getString(o).let { showSnackbar(it) }
            } else if (o is String) {
                showSnackbar(o)
            }

        }
    }


    /**
     * This is generic method based on the MutableLive Data Concept where value changed with Keyboard
     * will reflect and decide if value is false then it will hide the Keyboard.
     */
    private fun setUpHideKeyBoard() {
        viewModel.getHideKeyBoardEvent()
            .observe(this) { (requireActivity() as MainActivity).hideKeyboard() }
    }


    /**
     * This method is used to display the Snake Bar Toast message to user.
     *
     * @param message string to display.
     */
    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(
            requireActivity().findViewById(android.R.id.content)!!, message, Snackbar.LENGTH_SHORT
        )
        val view = snackbar.view
        val snackTV: TextView = view.findViewById(com.google.android.material.R.id.snackbar_text)
        snackTV.maxLines = 5
//        snackTV.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        snackbar.show()
    }

}