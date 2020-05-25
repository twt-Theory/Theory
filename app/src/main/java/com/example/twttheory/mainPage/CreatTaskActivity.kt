package com.example.twttheory.mainPage

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twttheory.R
import com.example.twttheory.exam.PostQuestion
import kotlinx.android.synthetic.main.success_toast_layout.*
import java.sql.Time

class CreatTaskActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creat_task)

        val settingButton : Button = findViewById(R.id.settings)
        var settingFragment : Fragment? = null
        var isSettingVisible = false;
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val raturnButton = findViewById<ImageView>(R.id.return_button)
        val addedQList = findViewById<LinearLayout>(R.id.questions)
        val typeChoose: RadioGroup = findViewById<RadioGroup>(R.id.choose_type)
        val b1 = findViewById<RadioButton>(R.id.single_selection)
        val b2 = findViewById<RadioButton>(R.id.multiple_selection)
        val b3 = findViewById<RadioButton>(R.id.text_question)
        val b4 = findViewById<RadioButton>(R.id.inventory_problem)
        val b5 = findViewById<RadioButton>(R.id.sequencing_question)
        val uploadBT = findViewById<Button>(R.id.bulk_import)
        var questionInfoList : ArrayList<QuestionItem> = ArrayList<QuestionItem>()
        var questionNumber : Int = 1      //题号


//        val questionList : RecyclerView = findViewById(R.id.added_questions)
        raturnButton.setOnClickListener {
            this.finish()
        }
        settingButton.setOnClickListener {
            if (!isSettingVisible){
                if (settingFragment == null){
                    settingFragment = SettingFragment
                    mFragmentTransaction.add(R.id.setting,settingFragment!!)
                    mFragmentTransaction.commit()         //不知道为什么没有效果（不能点一次隐藏，再点一次显示）
                }else{
                    mFragmentTransaction.show(settingFragment!!)
                }
                isSettingVisible = true
            }else{
                mFragmentTransaction.hide(settingFragment!!)
                isSettingVisible = false
            }
        }


//        questionList.layoutManager = LinearLayoutManager(this)
//        questionList.adapter = creatTaskAdapter

        typeChoose.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.single_selection ->{
                    //type == 0 是单选题
                    val makeQuestionView = MakeQuestionView(this,questionNumber)
                    makeQuestionView.type = 0
                    makeQuestionView.addSelectionBT.setOnClickListener {
                        val optionView = OptionView(this,0,makeQuestionView.optionNumber,false)
                        makeQuestionView.selectionLO.addView(optionView)
                        makeQuestionView.optionNumber += 1
                    }
                    addedQList.addView(makeQuestionView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                    questionNumber += 1;
                    group.clearCheck()
                }
                R.id.multiple_selection->{
                    //type == 1 是多选题
                    val makeQuestionView = MakeQuestionView(this,questionNumber)
                    makeQuestionView.type = 1
                    makeQuestionView.addSelectionBT.setOnClickListener {
                        val optionView = OptionView(this,1,makeQuestionView.optionNumber,false)
                        makeQuestionView.selectionLO.addView(optionView)
                        makeQuestionView.optionNumber += 1
                    }
                    addedQList.addView(makeQuestionView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                    questionNumber += 1;
                    group.clearCheck()
                }
                R.id.text_question->{
                    val makeQuestionView = MakeQuestionView(this,questionNumber)
                    makeQuestionView.type = 2
                    val textAnswer = EditText(this)
                    makeQuestionView.selectionLO.addView(textAnswer,ViewGroup.LayoutParams.MATCH_PARENT)
                    addedQList.addView(makeQuestionView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                    questionNumber += 1;
                    group.clearCheck()
                }
                R.id.inventory_problem->{
                    //type == 1 是多选题
                    val makeQuestionView = MakeQuestionView(this,questionNumber)
                    makeQuestionView.type = 3
                    makeQuestionView.addSelectionBT.setOnClickListener {
                        val optionView = OptionView(this,3,makeQuestionView.optionNumber,false)
                        makeQuestionView.selectionLO.addView(optionView)
                        makeQuestionView.optionNumber += 1
                    }
                    addedQList.addView(makeQuestionView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                    questionNumber += 1;
                    group.clearCheck()
                }
                R.id.sequencing_question->{
                    //type == 1 是多选题
                    val makeQuestionView = MakeQuestionView(this,questionNumber)
                    makeQuestionView.type = 4
                    makeQuestionView.addSelectionBT.setOnClickListener {
                        val optionView = OptionView(this,4,makeQuestionView.optionNumber,false)
                        makeQuestionView.selectionLO.addView(optionView)
                        makeQuestionView.optionNumber += 1
                    }
                    addedQList.addView(makeQuestionView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                    questionNumber += 1;
                    group.clearCheck()
                }
            }
        }
    }
    // 打开系统的文件选择器

}
