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
        for (i in 0 .. 10){
            tempQuestions.add(GetQuestion(i+1,
                "用细导线均匀密绕成长为1、半径为a(l>>a)、总匝数为N的螺线管,管内充满相对磁导率为u,的均匀磁介质。若线圈中载有稳恒电流1,则管中任意一点的",
                listOf("磁场强度大小为H=uoNI/1",
                    "磁感强度大小为B=4oH,NI",
                    "磁感强度大小为B=4,NI/1",
                    "磁场强度大小为H=NI/I."),
                0,true,false,1,-1,-1,1,1))
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
        for (i in 1 until questionList.size){
            val questionView = QuestionView(this,i)
            questionView.questionTV.text = questionList[i].question
            radioButtons.add(ArrayList())
            for (j in questionList[i].answer.indices){
                val optionView = OptionView(this,questionList[i].type,j,true)
                optionView.optionTV.text = questionList[i].answer[j]
                radioButtons[i-1].add(optionView.optionSingle)
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
