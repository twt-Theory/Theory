package com.example.twttheory.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toolbar
import com.example.twttheory.R
import com.example.twttheory.exam.ExamModel
import com.example.twttheory.exam.QuestionInfo
import com.example.twttheory.exam.getScore
import com.example.twttheory.service.RefreshState
import com.example.twttheory.useful.ToastType
import com.example.twttheory.useful.makeToast
import com.example.twttheory.views.OptionView
import com.example.twttheory.views.ResultItemView

class ResultActivity : AppCompatActivity() {
    lateinit var toolBar : Toolbar
    lateinit var resultLL : LinearLayout
    lateinit var finishTime :TextView
    lateinit var score : TextView
    lateinit var questionList : List<QuestionInfo>
    var  paperId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val returnButton = findViewById<ImageView>(R.id.return_button)
        returnButton.setOnClickListener {
            finish()
        }
        resultLL = findViewById(R.id.resultLL)
        finishTime = findViewById(R.id.finish_time)
        score = findViewById(R.id.score)
        paperId = intent.getIntExtra("paper_id",-1)
        getScore(paperId){
            when(it){
                is RefreshState.Success -> {
                    makeToast(this,"获取成功",ToastType.Success)
                    for (i in 0 until ExamModel.myScoreList[0].question.size){
                        val resultItemView = ResultItemView(this)
                        resultItemView.apply {
                            numberTV.text = (i+1).toString()
                            questionTV.text = ExamModel.myScoreList[0].question[i].question
                            for (j in 0 until ExamModel.myScoreList[0].question[i].options.size){
                                val optionView = OptionView(this@ResultActivity,ExamModel.myScoreList[0].question[i].type,j,true)
                                optionLL.addView(optionView)
                            }
                            val myAnswer = ExamModel.myScoreList[0].question[i].my_answer
                            val rightId = ExamModel.myScoreList[0].question[i].right_id
                            val score = ExamModel.myScoreList[0].question[i].score
                            myAnswerTV.text = "我的回答:$myAnswer"
                            rightIDTV.text  = "正确答案:$rightId"
                            scoreTV.text    = "本题得分:$score"
                        }
                        resultLL.addView(resultItemView)
                    }
                }
                else -> {
                    makeToast(this,"获取失败",ToastType.Error)
                }
            }
        }


    }



}

