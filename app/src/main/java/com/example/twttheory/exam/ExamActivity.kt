package com.example.twttheory.exam

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.example.twttheory.R
import com.example.twttheory.mainPage.OptionView
import com.example.twttheory.mainPage.TaskModel.recordInput
import com.example.twttheory.service.RefreshState
import com.example.twttheory.useful.ToastType
import com.example.twttheory.useful.makeToast
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class ExamActivity : AppCompatActivity() {
    lateinit var questionsLL: LinearLayout
    lateinit var radioButtons : MutableList<MutableList<RadioButton>>

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)

        var paperId = -1
        var password = "-1"
        val tempQuestions = ArrayList<GetQuestion>()
        radioButtons = ArrayList()  //存储radioButtons便于控制
        for (i in 0 until  recordInput.size){
            tempQuestions.add(GetQuestion(i+1,
                recordInput[i].question,
                recordInput[i].answer,
                recordInput[i].type,
                recordInput[i].is_necessary,
                recordInput[i].is_random,
                recordInput[i].score,
                recordInput[i].need_question,
                recordInput[i].need_answer,
                recordInput[i].max_select,
                recordInput[i].min_select))
        }
        //返回按钮
        val returnImage = findViewById<ImageView>(R.id.return_button)
        returnImage.setOnClickListener {
            //此处可以暂停问卷之类的
            this.finish()
        }

        val submitBT = findViewById<Button>(R.id.submit)
        submitBT.setOnClickListener {
            //此处上传数据

            //startActivity()   //想打开结果查看页面，之后再写

            this.finish()
        }

        questionsLL = findViewById(R.id.questions)
//        getExam(paperId, password) { refreshState ->
//            when (refreshState) {
//                is RefreshState.Success -> {
//                    //获取问卷
//                    //获取问题列表
//                    //presentPaper()    //传入问题列表
//
//                }
//                is RefreshState.Failure -> {
//
//                }
//            }
//            questionsLL = findViewById(R.id.questions)
//
//
//        }
        presentPaper(tempQuestions)



    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun presentPaper(questionList : MutableList<GetQuestion>){
        for (i in 0 until questionList.size){
            val questionView = QuestionView(this,i)
            questionView.questionTV.text = questionList[i].question
            radioButtons.add(ArrayList())
            for (j  in 0 until questionList[i].answer.size){

                val optionView = OptionView(this,questionList[i].type,j,true)
                optionView.optionTV.text = questionList[i].answer[j]
                radioButtons[i].add(optionView.optionSingle)
                questionView.optionsLL.addView(optionView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                //手动把这些radio button写成只能单选
                if (questionList[i].type == 0){
                    optionView.optionSingle.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked){
                            for (i in radioButtons[i-1]){
                                if (i!=buttonView){
                                    i.isChecked = false
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
