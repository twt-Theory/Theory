package com.example.twttheory.manage

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.twttheory.R
import com.example.twttheory.exam.*
import com.example.twttheory.mainPage.*
import com.example.twttheory.service.RefreshState
import com.example.twttheory.useful.*
import com.example.twttheory.views.MakeQuestionView
import com.example.twttheory.views.OptionView

class ManageActivity : AppCompatActivity() {

    //用来装载题目的横向的LinearLayout
    lateinit var questionsLL : LinearLayout
    //存储radioButtons便于控制
    lateinit var radioButtons : MutableList<MutableList<RadioButton>>
    lateinit var changedQuestionList : MutableList<ChangedQuestion>
    var paper_id: Int = -1
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        //从intent中获取paper_id
        paper_id = intent.getIntExtra("paper_id",-1)
        //显示问卷设置的碎片
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val  settingFragment = SettingFragment
        //显示
        mFragmentTransaction.add(R.id.setting,settingFragment)
        mFragmentTransaction.commit()
        //返回图片的点击事件
        val returnImage = findViewById<ImageView>(R.id.return_button).setOnClickListener{
            this.finish()
        }
        //存储radioButtons便于控制
        radioButtons = ArrayList()
        //用来装载题目的横向的LinearLayout
        questionsLL = findViewById(R.id.questionsLL)

        var paperId = intent.getIntExtra("paper_id",-1)
        getChangeExam(paper_id){
            when(it){
                is RefreshState.Success -> {
                    changedQuestionList = ExamModel.myChangedQuestion
                    presentQuestions(changedQuestionList)
                }
                else -> {

                }
            }
        }
        //生成临时数据


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
   @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
   fun presentQuestions(questionList : MutableList<ChangedQuestion>){

           for (i in 0 until questionList.size){
               //新建题目
               val makeQuestionView =
                   MakeQuestionView(
                       this,
                       i,
                       questionList[i].type
                   )
               //由于是之前出的题目，所以把edittext 的文字设置成题目
               makeQuestionView.questionEt.setText(questionList[i].question)
               //让"修改该题"按钮可见
               makeQuestionView.modifyBT.visibility = View.VISIBLE
               //如果这一题的按钮被点击了，就把这一题加入到changedList中
               makeQuestionView.modifyBT.setOnClickListener {
                   ManageModel.changedList.add(questionList[i])
               }
               //把单选题的radio button 放到一个列表中方便后面的操作，这里是在列表中放一个列表，做准备
               radioButtons.add(ArrayList())
               for (j  in 0 until questionList[i].answer.size){
                    //添加选项
                   val optionView = OptionView(
                       this,
                       questionList[i].type,
                       j,
                       false
                   )

                   optionView.optionContent.setText(questionList[i].answer[j])
                   radioButtons[i].add(optionView.optionSingle)
                   makeQuestionView.selectionLO.addView(optionView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)

                   //手动把这些radio button写成只能单选
                   if (questionList[i].type == 0){
                       //把正确选项选上
                       if (questionList[i].right_id != "-1"){
                           //很不明白这里为什么要减4，不减会发生数组越界，index：4，size：1
                           radioButtons[i][questionList[i].right_id.toInt()-65-4].isChecked = true
                       }
                       //设置选择监听
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
                   if (questionList[i].type == 1) {
                       //把正确选项选上
                       if (questionList[i].right_id != "-1") {
                           for (l in questionList[i].right_id){
                               if (l != '#'){
                                   radioButtons[i][l.toInt() - 65].isChecked = true
                               }
                           }

                       }
                   }

               }
               questionsLL.addView(makeQuestionView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
           }


   }
}
