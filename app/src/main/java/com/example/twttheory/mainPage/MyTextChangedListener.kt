package com.example.twttheory.mainPage

import android.text.Editable
import android.text.TextWatcher
import com.example.twttheory.exam.ChangedQuestion
import java.text.ParsePosition

class MyTextChangedListener : TextWatcher {
    lateinit var changedText : String
    lateinit var changedFlag : MutableList<Boolean>
    var position: Int = 0
    override fun afterTextChanged(s: Editable?) {
        changedText = s.toString()
        changedFlag[position] = true
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }
}