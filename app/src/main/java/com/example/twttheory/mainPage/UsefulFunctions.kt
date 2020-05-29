package com.example.twttheory.mainPage

import android.R
import android.widget.Toast


object UsefulFunctions {
    fun initQuestion( type : Int ) = ChangableData.ChangablePostQuestion(
        "-1",
        ArrayList(),
        "-1",
        type,
        true,
        false,
        1,
        -1,
        -1,
        -1,
        -1
    )

}