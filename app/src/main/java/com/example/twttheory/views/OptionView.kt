package com.example.twttheory.views

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.example.twttheory.R
import kotlinx.android.synthetic.main.activity_creat_task.view.*
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

    var questionNum : Int = 0

    //type ： 单选题还是多选题
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)                           //count 用来记录量表题是加线还是加分段
    @JvmOverloads
    constructor(context: Context, type : Int, alphabetNum : Int, isExam : Boolean,count : Int = 0) : super(context) {
        initView(context, alphabetNum)

        gravity = Gravity.LEFT
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
                if (count%2 == 0){
                    addView(line,lineLP)
                }else{
                    addView(optionSingle,optionSingleLP)
                    if (isExam){
                        addView(optionTV,optionTVLP)
                    }else{
                        addView(optionContent,optionContentLP)
                    }
                }
            }
            4->{
                addView(sequenceImage,sequenceImageLP)
            }
        }

        if (type!= 3){
            addView(optionName,optionNameLP)
            if (isExam){
                addView(optionTV,optionTVLP)
            }else{
                addView(optionContent,optionContentLP)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
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
        lineLP = LayoutParams(5,100)
        line.setBackgroundColor(Color.BLACK)
        lineLP.marginStart = 40
        lineLP.gravity = Gravity.LEFT

        sequenceImage = ImageView(context)
        sequenceImageLP = LayoutParams(60,60)

        optionTV.setOnClickListener {
            optionSingle.isChecked = !optionSingle.isChecked

        }


    }

}
