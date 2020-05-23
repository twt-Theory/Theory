package com.example.twttheory.mainPage

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import java.util.jar.Attributes

//动态加入这个view， 可以直接产生选项
class OptionView : LinearLayout {
    lateinit var optionName : TextView
    lateinit var optionSingle : RadioButton
    lateinit var optionMultiple : CheckBox
    lateinit var optionContent : EditText
    lateinit var optionNameLP : LayoutParams
    lateinit var optionSingleLP : LayoutParams
    lateinit var optionMultipleLP : LayoutParams
    lateinit var optionContentLP : LayoutParams
    //type ： 单选题还是多选题
    constructor(context: Context, type : Int, alphabetNum : Int) : super(context) {
        initView(context, alphabetNum)
        addView(optionName,optionNameLP)
        gravity = Gravity.CENTER
        orientation = LinearLayout.HORIZONTAL
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,100)

        when(type){
            0->{
                optionName.hint = "输入选项"
                addView(optionSingle,optionSingleLP)

            }
            1->{
                addView(optionMultiple,optionMultipleLP)
            }
        }
        addView(optionContent,optionContentLP)
    }

    fun initView(context: Context, alphabetNum: Int){
        optionName = TextView(context)
        optionSingle = RadioButton(context)
        optionMultiple = CheckBox(context)
        optionContent = EditText(context)

        optionNameLP = LinearLayout.LayoutParams(60,ViewGroup.LayoutParams.WRAP_CONTENT)
        optionSingleLP = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        optionMultipleLP = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        optionContentLP = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)

        //设置选项的序号
        optionName.text = (alphabetNum + 'A'.toInt()).toChar().toString()
        optionName.textSize = 20F


    }

}
