package com.example.twttheory.views

import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout

class QuestionView : ConstraintLayout{
    lateinit var numberTV : TextView
    lateinit var numberTVLP : ConstraintLayout.LayoutParams

    lateinit var questionTV : TextView
    lateinit var questionTVLP : ConstraintLayout.LayoutParams

    lateinit var optionsLL : LinearLayout
    lateinit var optionsLLLP : ConstraintLayout.LayoutParams

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    constructor(context: Context, questionNumber : Int) : super(context){
        initView(context,questionNumber)
        val number = questionNumber+1
        numberTV.text = "$number."
        addView(numberTV,numberTVLP)
        addView(questionTV,questionTVLP)
        addView(optionsLL,optionsLLLP)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun initView(context: Context, questionNumber: Int){
        numberTV = TextView(context)
        numberTV.id = View.generateViewId()
        numberTVLP = ConstraintLayout.LayoutParams(LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)


        questionTV = TextView(context)
        questionTV.id = View.generateViewId()
        questionTVLP = ConstraintLayout.LayoutParams(LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        questionTVLP.baselineToBaseline = numberTV.id
        questionTVLP.leftToRight = numberTV.id
        questionTVLP.marginStart = 30

        numberTVLP.apply {
            rightToLeft = questionTV.id
        }

        optionsLL = LinearLayout(context)
        optionsLL.orientation = LinearLayout.VERTICAL
        optionsLLLP = ConstraintLayout.LayoutParams(LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        optionsLLLP.apply {
            topToBottom = questionTV.id
            leftToLeft = questionTV.id
           // orientation = LinearLayout.VERTICAL    并不是给layout params设置方向，应该是给linear layout 设置方向
        }
    }
}