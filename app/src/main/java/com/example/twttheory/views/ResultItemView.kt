package com.example.twttheory.views

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.twttheory.R

class ResultItemView : LinearLayout {
    var optionLL : LinearLayout
    var numberTV : TextView
    var questionTV : TextView
    var myAnswerTV : TextView
    var scoreTV: TextView
    var rightIDTV : TextView
    constructor(context: Context) : super(context){
        View.inflate(context,R.layout.item_result,this)
        optionLL = findViewById(R.id.optionLL)
        numberTV = findViewById(R.id.number)
        questionTV = findViewById(R.id.question)
        myAnswerTV = findViewById(R.id.myAnswer)
        scoreTV = findViewById(R.id.score)
        rightIDTV = findViewById(R.id.right_id)
    }
}