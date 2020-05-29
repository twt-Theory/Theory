package com.example.twttheory.mainPage

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginLeft
import com.example.twttheory.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MakeQuestionView : ConstraintLayout {
    lateinit var typeImage: ImageView
    lateinit var typeImageLP : ConstraintLayout.LayoutParams
    lateinit var questionTV : TextView
    lateinit var questionTVLP : ConstraintLayout.LayoutParams
    lateinit var questionEt : EditText
    lateinit var questionEtLP : ConstraintLayout.LayoutParams
    lateinit var valueTV : TextView
    lateinit var valueTVLP: ConstraintLayout.LayoutParams
    lateinit var valueET : EditText
    lateinit var valueETLP : ConstraintLayout.LayoutParams
    lateinit var selectionLO : LinearLayout
    lateinit var selectionLOLP : ConstraintLayout.LayoutParams
    lateinit var settingLL : LinearLayout
    lateinit var settingLLLP :ConstraintLayout.LayoutParams
    lateinit var addSelectionBT : Button
    lateinit var addSelectionBTLP : ConstraintLayout.LayoutParams
    lateinit var thisLayoutPrams : LinearLayout.LayoutParams
    lateinit var needAET : EditText
    lateinit var needQET : EditText

    lateinit var necessaryCB : CheckBox
    lateinit var randomCB : CheckBox
    lateinit var jumpCB : CheckBox
    var optionNumber : Int = 0
    var questionNumber : Int = 0
    var added : Boolean = false


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    constructor (context: Context, questionNumber: Int,type:Int) : super(context){
        initView(context,questionNumber,type)
        layoutParams = thisLayoutPrams

        this.questionNumber = questionNumber
        addView(typeImage,typeImageLP)
        addView(questionTV,questionTVLP)
        addView(questionEt,questionEtLP)
        addView(valueTV,valueTVLP)
        addView(valueET,valueETLP)
        addView(selectionLO,selectionLOLP)
        addView(settingLL,settingLLLP)
        if(type == 0 || type == 1 || type == 3 || type == 4)
        addView(addSelectionBT,addSelectionBTLP)

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun initView(context: Context, questionNumber : Int,type: Int){
        thisLayoutPrams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        thisLayoutPrams.marginStart = 300
        //标识题目类型的图片
        typeImage = ImageView(context)
        typeImage.id = View.generateViewId()
        typeImage.background = Drawable.createFromPath("Drawable/single.jpg")
        typeImageLP = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        //表示题号的textview
        questionTV = TextView(context)
        questionTV.text = questionNumber.toString()+"."
        questionTV.id = View.generateViewId()
        questionTVLP = ConstraintLayout.LayoutParams(LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        //出题时题目的输入框
        questionEt = EditText(context)
        questionEt.id = View.generateViewId()
        questionEtLP = ConstraintLayout.LayoutParams(500,ViewGroup.LayoutParams.WRAP_CONTENT)
        questionEtLP.apply {
            leftToRight = typeImage.id
            baselineToBaseline = questionTV.id
        }
        //提示输入分值的textview
        valueTV = TextView(context)
        valueTV.text = "分值:"
        valueTV.id = View.generateViewId()
        valueTVLP = ConstraintLayout.LayoutParams(LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        valueTVLP.apply {
            leftToRight = questionEt.id
            baselineToBaseline = questionEt.id
        }
        //读取分值的edit text
        valueET = EditText(context)
        valueET.hint = "默认为1"
        valueETLP = ConstraintLayout.LayoutParams(LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            leftToRight = valueTV.id
            baselineToBaseline = valueTV.id
        }
        //放选项的LinearLayout
        selectionLO = LinearLayout(context)
        selectionLO.id = View.generateViewId()
        selectionLO.orientation = LinearLayout.VERTICAL
        selectionLOLP = ConstraintLayout.LayoutParams(500,LayoutParams.WRAP_CONTENT)
        selectionLOLP.apply {
            topToBottom = questionEt.id
            leftToLeft = questionEt.id
        }
        //是否必答的checkBox
        necessaryCB = CheckBox(context)
        necessaryCB.apply {
            text = "该题必答"
            id = View.generateViewId()
        }
        // 选项随机的check box
        randomCB  = CheckBox(context)
        randomCB.apply {
            text = "选项随机"
            id = View.generateViewId()
        }
        //题目跳转的check box
        jumpCB = CheckBox(context)
        jumpCB.apply {
            text = "题目跳转"
            id = View.generateViewId()
        }
        //题目跳转需要答哪题
        needQET = EditText(context)
        needQET.apply {
            id = View.generateViewId()
            hint = "题号"
        }
        //题目跳转需要答的题选的是哪个选项
        needAET = EditText(context)
        needAET.apply {
            id = View.generateViewId()
            hint = "选项"
        }
        //放以上这些设置的LinearLayout
        settingLL = LinearLayout(context)
        settingLL.apply {
            orientation = RadioGroup.VERTICAL
            //加入“是否必答”复选框
            addView(necessaryCB,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
            //如果是“单选题”或“多选题”，加入“选项随机”的复选框
            if (type == 0 || type == 1 ){
                addView(randomCB,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
            }
            //加入“题目跳转”复选框
            addView(jumpCB,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
            //如果“题目跳转”被选中，加入“跳转需要答的题目”和“需要选的选项”两个EditText
            jumpCB.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    if (added == false){
                        addView(needQET,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT)
                        addView(needAET,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT)
                        added = true

                    }
                    needQET.visibility = View.VISIBLE
                    needAET.visibility = View.VISIBLE
                }else{
                    //不被选中时，隐藏这两个edittext
                    needQET.visibility = View.INVISIBLE
                    needAET.visibility = View.INVISIBLE

                }
            })

        }
        //上面这个LinearLayout 的Layout Params
        settingLLLP = ConstraintLayout.LayoutParams(LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        //Selection linear layout layout prams
        settingLLLP.apply {
            topToTop = selectionLO.id
            leftToRight = selectionLO.id
        }
        //添加选项 的按钮
        addSelectionBT  = Button(context)
        addSelectionBT.apply {
            id = View.generateViewId()
            background = getDrawable(context,R.drawable.myrect)
            when(type){
                0->text = "添加选项"
                1->text = "添加选项"
                2->visibility = View.INVISIBLE
                3->text = "添加分段"
                4->text = "添加序号项"
            }


        }
        //添加选项的按钮的Layout Params
        addSelectionBTLP = ConstraintLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
        addSelectionBTLP.topToBottom = selectionLO.id
        addSelectionBTLP.leftToLeft = selectionLO.id


    }
//    val sNextGeneratedId : AtomicInteger = AtomicInteger(1);
//    fun mGenerateViewId() : Int{
//        while (true) {
//            val result = sNextGeneratedId.get();
//            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
//            var newValue = result + 1;
//            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
//            if (sNextGeneratedId.compareAndSet(result, newValue)) {
//                return result;
//            }
//        }
//    }

}