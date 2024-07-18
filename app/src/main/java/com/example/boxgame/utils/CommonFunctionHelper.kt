package com.example.boxgame.utils

import android.os.Build
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation


object CommonFunctionHelper {

    fun getUniqueId(): String {
        return (Build.BOARD.length % 10).toString() +
                (Build.BRAND.length % 10).toString() +
                (Build.DEVICE.length % 10).toString() +
                (Build.DISPLAY.length % 10).toString() +
                (Build.HOST.length % 10).toString() +
                (Build.ID.length % 10).toString() +
                (Build.MANUFACTURER.length % 10).toString() +
                (Build.MODEL.length % 10).toString() +
                (Build.PRODUCT.length % 10).toString() +
                (Build.TAGS.length % 10).toString() +
                (Build.TYPE.length % 10).toString() +
                (Build.USER.length % 10).toString()
    }

    fun setFadeAnimation(view: View) {
        //val anim = AlphaAnimation(0.0f, 1.0f)
        val anim = ScaleAnimation(
            0.0f,
            1.0f,
            0.0f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )

        anim.duration = 600
        view.startAnimation(anim)
    }

}