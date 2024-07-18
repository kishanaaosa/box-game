package com.example.boxgame.ui.game.model

import com.example.boxgame.utils.Constant.GRAY

data class BoxModel(
    var boxIndex: Int = 0,
    var boxValue: Int = 0,
    var boxBgColor: String = GRAY,
    var isBoxSelected: Boolean = false,
    var isClickable: Boolean = false
)
