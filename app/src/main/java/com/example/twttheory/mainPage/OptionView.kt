package com.example.twttheory.mainPage

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.jar.Attributes

//动态加入这个view， 可以直接产生选项
class OptionView : LinearLayout {
    lateinit var optionName : TextView
    lateinit var optionSingle : RadioButton
    lateinit var optionMultiple : CheckBox
    lateinit var optionContent : EditText
    lateinit var optionTV : TextView

    lateinit var optionNameLP : LayoutParams
    lateinit var optionSingleLP : LayoutParams
    lateinit var optionMultipleLP : LayoutParams
    lateinit var optionContentLP : LayoutParams
    lateinit var sequenceImage : ImageView
    lateinit var sequenceImageLP : LayoutParams
    lateinit var optionTVLP : LayoutParams
    lateinit var line : View
    lateinit var lineLP : LayoutParams

    //type ： 单选题还是多选题
    constructor(context: Context, type : Int, alphabetNum : Int,isExam : Boolean) : super(context) {
        initView(context, alphabetNum)

        gravity = Gravity.CENTER
        orientation = LinearLayout.HORIZONTAL
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,100)

        when(type){
            0->{
                addView(optionSingle,optionSingleLP)

            }
            1->{
                addView(optionMultiple,optionMultipleLP)
            }
            3->{
                if (alphabetNum!=0){
                    addView(line,lineLP)
                }
                addView(optionSingle,optionSingleLP)
            }
            4->{
                addView(sequenceImage,sequenceImageLP)
            }
        }

        addView(optionName,optionNameLP)
        if (isExam){
            addView(optionTV,optionTVLP)
        }else{
            addView(optionContent,optionContentLP)
        }


    }

    fun initView(context: Context, alphabetNum: Int){
        optionName = TextView(context)
        optionSingle = RadioButton(context)
        optionMultiple = CheckBox(context)
        optionContent = EditText(context)
        optionTV = TextView(context)

        optionNameLP = LinearLayout.LayoutParams(60,ViewGroup.LayoutParams.WRAP_CONTENT)
        optionSingleLP = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        optionMultipleLP = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        optionContentLP = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        optionTVLP = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)


        optionSingle.setOnClickListener {
            optionTV.setOnClickListener {
                optionSingle.isChecked = !optionSingle.isChecked
            }
        }
        //设置选项的序号
        optionName.text = (alphabetNum + 'A'.toInt()).toChar().toString()
        optionName.textSize = 20F
        optionName.setOnClickListener {
            optionSingle.isChecked = !optionSingle.isChecked

        }

        line = View(context)
        lineLP = LayoutParams(50,200)

        sequenceImage = ImageView(context)
        sequenceImageLP = LayoutParams(60,60)

        optionTV.setOnClickListener {
            optionSingle.isChecked = !optionSingle.isChecked

        }


    }
    fun notifyOtherOptionIsChecked(){
        optionSingle.isChecked = false
    }

}
