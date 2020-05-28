package com.example.twttheory.manage

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.twttheory.R
import com.example.twttheory.exam.*
import com.example.twttheory.mainPage.MakeQuestionView
import com.example.twttheory.mainPage.MyTextChangedListener
import com.example.twttheory.mainPage.OptionView
import com.example.twttheory.mainPage.SettingFragment
import com.example.twttheory.mainPage.UsefulFunctions.initQuestion
import com.example.twttheory.manage.ManageModel.changedList
import com.example.twttheory.service.RefreshState
import com.example.twttheory.useful.*

class ManageActivity : AppCompatActivity() {

    var paper_id: Int = -1
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        paper_id = intent.getIntExtra("paper_id",-1)
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val  settingFragment = SettingFragment
        val returnImage = findViewById<ImageView>(R.id.return_button).setOnClickListener{
            this.finish()
        }
        mFragmentTransaction.add(R.id.setting,settingFragment)
        mFragmentTransaction.commit()
        //修改题目
        val changQuestionLL = findViewById<LinearLayout>(R.id.changeQuesLayout)
        var questionList : MutableList<ChangedQuestion> = ArrayList<ChangedQuestion>()

        val changedFlag : MutableList<Boolean> = ArrayList<Boolean>()
        var questionNumber = 1
        val changeQuestionBT = findViewById<Button>(R.id.changeQuesBt)
        changeQuestionBT.setOnClickListener {
            for (i in 0 .. questionList.size){
                val makeQuestionView = MakeQuestionView(this, questionNumber,questionList[i].type)
                //makeQuestionView.questionEt.text = questionList[i].question
                for (j in 0 .. questionList[i].answer.size){
                    val optionView = OptionView(this,questionList[i].type,j,false)
                    //val listener = MyTextChangedListener()

                    changedList.add(ManageModel.initQuestion(0))
//                    listener.changedText = changedList[i].answer[j]
//                    listener.changedFlag = changedFlag
//                    listener.position = i
//                    optionView.optionContent.addTextChangedListener(listener)

                    makeQuestionView.addView(optionView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                }
                questionNumber += 1
                changQuestionLL.addView(makeQuestionView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)

            }

        }
        //选择专业限制
        val chooseLimiter: AlertDialog
        val confirmBt = findViewById<Button>(R.id.confirm_button).apply {
            setOnClickListener {
                val case = SettingFragment.changeSettings(paper_id)
                val msg: String = when (case) {
                    0 -> "问卷修改成功！"
                    1 -> "请设置问卷名称！"
                    2 -> "请设置问卷描述！"
                    3 -> "请设置答题时长！"
                    4 -> "请设置答题次数！"
                    5 -> "请设置问卷密码！"
                    else -> "问卷修改失败！"
                }
                if (case != 0) makeToast(this@ManageActivity, msg, ToastType.Error)
                else makeToast(this@ManageActivity, msg, ToastType.Success)
            }
        }
        val changeQuesBt = findViewById<Button>(R.id.changeQuesBt).apply {
            setOnClickListener {
                //跳转至修改题目页面
            }
        }
        val pauseBt = findViewById<Button>(R.id.pauseBt).apply {
            setOnClickListener {
                //暂停功能尚未实现！！！！！！！！！！！！
//                pauseExam(paper_id) {
//                    when (it) {
//                        is RefreshState.Success -> makeToast(
//                            this@ManageActivity,
//                            "暂停问卷成功！",
//                            ToastType.Success
//                        )
//                        else -> makeToast(this@ManageActivity, "暂停问卷失败！", ToastType.Error)
//                    }
//                }
            }
        }
        val deleteBt = findViewById<Button>(R.id.deleteBt).apply {
            setOnClickListener {
                deleteExam(paper_id) {
                    when (it) {
                        is RefreshState.Success -> {
                            makeToast(this@ManageActivity, "删除问卷成功！", ToastType.Success)
                            finish()
                        }
                        else -> makeToast(this@ManageActivity, "删除问卷失败！", ToastType.Error)
                    }
                }
            }
        }
    }
}
