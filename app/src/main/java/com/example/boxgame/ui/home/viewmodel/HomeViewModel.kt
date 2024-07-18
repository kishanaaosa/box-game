package com.example.boxgame.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.boxgame.R
import com.example.boxgame.base.ViewModelBase
import com.example.boxgame.utils.SingleLiveEvent
import com.example.boxgame.utils.Validation
import com.example.boxgame.utils.sharedpref.MyPreference
import com.example.boxgame.utils.sharedpref.PrefKey.NO_OF_BOX
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val myPreference: MyPreference) :
    ViewModelBase() {
    lateinit var numberOfBox: MutableLiveData<String>
    lateinit var onNextClick: SingleLiveEvent<Boolean>


    fun initVariables() {
        numberOfBox = MutableLiveData("")
        onNextClick = SingleLiveEvent()
    }

    fun onNextClick() {
        hideKeyboard()
        when {
            !Validation.isNotNull(
                numberOfBox.value.toString().trim()
            ) -> {
                showSnackbarMessage(R.string.error_enter_number_of_box)
            }
            !Validation.isValidBoxSize(
                numberOfBox.value.toString().trim()
            ) -> {
                showSnackbarMessage(R.string.error_enter_number_of_box_min_value)
            }

            else -> {
                myPreference.setValueInt(NO_OF_BOX, numberOfBox.value.toString().trim().toInt())
                onNextClick.call()
            }
        }
    }
}
