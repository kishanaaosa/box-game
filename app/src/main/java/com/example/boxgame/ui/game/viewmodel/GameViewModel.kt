package com.example.boxgame.ui.game.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.boxgame.base.ViewModelBase
import com.example.boxgame.ui.game.model.BoxModel
import com.example.boxgame.utils.Constant.GRAY
import com.example.boxgame.utils.Constant.RED
import com.example.boxgame.utils.sharedpref.MyPreference
import com.example.boxgame.utils.sharedpref.PrefKey
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val myPreference: MyPreference) :
    ViewModelBase() {

    lateinit var boxList: ArrayList<BoxModel>
    var colorCount: Int = 0
    var selectionColor: MutableLiveData<String> = MutableLiveData(RED)

    fun initVariables() {
        boxList = arrayListOf()
        val noOfBox = myPreference.getValueInt(PrefKey.NO_OF_BOX, 0)
        for (i in 1..noOfBox) {
            boxList.add(BoxModel(i - 1, i, GRAY, false, false))
        }
    }
}