package com.example.twttheory.exam

import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.twttheory.R
import com.example.twttheory.enums.PaperType
import com.example.twttheory.views.OptionView
import com.example.twttheory.mainPage.TaskModel.paperName
import com.example.twttheory.mainPage.TaskModel.recordInput
import com.example.twttheory.service.RefreshState
import com.example.twttheory.useful.ToastType
import com.example.twttheory.useful.makeToast
import com.example.twttheory.views.QuestionView

class ExamActivity : AppCompatActivity() {
    lateinit var questionsLL: LinearLayout
    lateinit var radioButtons : MutableList<MutableList<RadioButton>>
    lateinit var paperNameTV : TextView
    lateinit var myAnswer : ArrayList<String>

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)

        var paperId = intent.getIntExtra("paper_id",-1)
        var password = "-1"

        questionsLL = findViewById(R.id.questions)
        paperNameTV = findViewById<TextView>(R.id.paperName)
        paperNameTV.text = paperName
        radioButtons = java.util.ArrayList()  //存储radioButtons便于控制
        myAnswer = ArrayList()
        //获取题目
        getExam(paperId,password){
            when(it){
                is RefreshState.Success -> {
                    var questionList = ExamModel.myQuestions
                    presentPaper(questionList)

                }
                else -> {

                }
            }
        }
        //返回按钮
        val returnImage = findViewById<ImageView>(R.id.return_button)
        returnImage.setOnClickListener {
            //此处可以暂停问卷之类的
            this.finish()
        }
        //提交按钮
        val submitBT = findViewById<Button>(R.id.submit)
        submitBT.setOnClickListener {
            //提交数据
            solveExam(paperId,-1,myAnswer){
                when(it){
                    is RefreshState.Success -> {
                        makeToast(this,"提交成功",ToastType.Success)

                    }
                    else -> {
                        makeToast(this,"提交失败",ToastType.Error)
                    }
                }
            }

            //startActivity()   //想打开结果查看页面，之后再写

            this.finish()
        }






    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun presentPaper(questionList : MutableList<GetQuestion>){
        for (i in 0 until questionList.size){
            val questionView = QuestionView(this, i)
            myAnswer.add("-1")
            questionView.questionTV.text = questionList[i].question
            radioButtons.add(ArrayList())
            for (j  in 0 until questionList[i].answer.size){

                val optionView = OptionView(
                    this,
                    questionList[i].type,
                    j,
                    true
                )
                optionView.optionTV.text = questionList[i].answer[j]
                radioButtons[i].add(optionView.optionSingle)
                questionView.optionsLL.addView(optionView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                //手动把这些radio button写成只能单选
                if (questionList[i].type == 0){
                    optionView.optionSingle.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked){
                            myAnswer[i] = ('A'.toInt()+radioButtons[i-1].indexOf(buttonView)).toString()
                            for (k in radioButtons[i-1]){
                                if (k!=buttonView){
                                    k.isChecked = false
                                }

                            }
                        }
                    }
                }

            }
            questionsLL.addView(questionView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)

        }

    }
}
