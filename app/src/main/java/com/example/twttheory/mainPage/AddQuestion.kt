package com.example.twttheory.mainPage

import android.widget.ImageView
import java.io.File

data class AddQuestion(
    var type : Int = 1,
    var selectionNum: Int = 4,
    var value : Float = 1f,
    var necessary : Boolean = true,
    var random : Boolean = false,
    var jump : Boolean = false,
    var images : MutableList<File>? = null,
    var questionStem : String,
    var selections : MutableList<String>
    ) {
}