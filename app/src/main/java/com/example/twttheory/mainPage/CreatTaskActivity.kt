package com.example.twttheory.mainPage

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.twttheory.R
import com.example.twttheory.exam.createExam
import com.example.twttheory.mainPage.TaskModel.questionNumber
import com.example.twttheory.mainPage.TaskModel.recordInput
import com.example.twttheory.mainPage.TaskModel.toStandard
import com.example.twttheory.mainPage.UsefulFunctions.initQuestion
import com.example.twttheory.service.RefreshState

class CreatTaskActivity : AppCompatActivity() {
    lateinit var radioButtons : MutableList<MutableList<RadioButton>>

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creat_task)

        val settingButton : Button = findViewById(R.id.settings)
        var settingFragment : Fragment? = null
        var isSettingVisible = false;
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val returnButton = findViewById<ImageView>(R.id.return_button)
        val addedQList = findViewById<LinearLayout>(R.id.questions)
        val typeChoose: RadioGroup = findViewById<RadioGroup>(R.id.choose_type)
        val b1 = findViewById<RadioButton>(R.id.single_selection)
        val b2 = findViewById<RadioButton>(R.id.multiple_selection)
        val b3 = findViewById<RadioButton>(R.id.text_question)
        val b4 = findViewById<RadioButton>(R.id.inventory_problem)
        val b5 = findViewById<RadioButton>(R.id.sequencing_question)
        val uploadBT = findViewById<Button>(R.id.bulk_import)
        var questionInfoList : ArrayList<QuestionItem> = ArrayList<QuestionItem>()
        val submitBT = findViewById<Button>(R.id.submit)
        val paperTypeRG = findViewById<RadioGroup>(R.id.type)
        //用来数量表题是应该加线还是加选项
        var countForInventoryProblem : Int = 0
        //存储radioButtons便于控制
        radioButtons = ArrayList()

        //选择问卷类型的radio group 的监听事件
        paperTypeRG.setOnCheckedChangeListener{group, checkedId ->
            when(checkedId){
                R.id.questionare -> {

                }
            }

        }
        //返回按钮的点击监听
        returnButton.setOnClickListener {
            this.finish()
        }
        //设置权限按钮的点击监听
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
        //题目类型选择的radio group的监听
        typeChoose.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.single_selection ->{
                    //type == 0 是单选题
                    recordInput.add(initQuestion(0))
                    radioButtons.add(ArrayList())
                    val makeQuestionView = MakeQuestionView(this,questionNumber,0)
                    makeQuestionView.addSelectionBT.setOnClickListener {
                        val optionView = OptionView(this,0,makeQuestionView.optionNumber,false)
                        radioButtons[makeQuestionView.questionNumber-1].add(optionView.optionSingle)
                        recordInput[makeQuestionView.questionNumber-1].answer.add("-1")
                        val listener = MyTextChangedListener(WigitId.OPTION_CONTENT, makeQuestionView.questionNumber-1)
                        listener.optionNum =  makeQuestionView.optionNumber
                        optionView.optionContent.addTextChangedListener(listener)
                        optionView.optionSingle.setOnCheckedChangeListener { buttonView, isChecked ->
                            if (isChecked){
                                for (i in radioButtons[makeQuestionView.questionNumber-1]){
                                    if (i!=buttonView){
                                        //把其他选项设置为unchecked
                                        i.isChecked = false
                                    }else{
                                        //记录该题的答案
                                        recordInput[makeQuestionView.questionNumber-1].right_id = (makeQuestionView.optionNumber+65).toString()
                                    }

                                }
                            }
                        }
                        makeQuestionView.selectionLO.addView(optionView)
                        makeQuestionView.optionNumber += 1
                    }
                    bindWidgetWithVariable(makeQuestionView)
                    addedQList.addView(makeQuestionView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                    questionNumber += 1;
                    group.clearCheck()
                }
                R.id.multiple_selection->{
                    //type == 1 是多选题
                    recordInput.add(initQuestion(1))
                    radioButtons.add(ArrayList())
                    val makeQuestionView = MakeQuestionView(this,questionNumber,1)
                    makeQuestionView.addSelectionBT.setOnClickListener {
                        val optionView = OptionView(this,1,makeQuestionView.optionNumber,false)
                        recordInput[makeQuestionView.questionNumber-1].answer.add("-1")
                        val listener = MyTextChangedListener(WigitId.OPTION_CONTENT, makeQuestionView.questionNumber-1)
                        listener.optionNum =  makeQuestionView.optionNumber
                        optionView.optionContent.addTextChangedListener(listener)
                        //
                        optionView.optionMultiple.setOnCheckedChangeListener { buttonView, isChecked ->
                            if(recordInput[makeQuestionView.questionNumber-1].right_id == "-1"){
                                //等于-1（尚未设置答案），直接赋值
                                recordInput[makeQuestionView.questionNumber-1].right_id = (makeQuestionView.optionNumber+65).toString()
                        }else{
                                //不等于-1（前面选过其他答案），加上"#X"
                                recordInput[makeQuestionView.questionNumber-1].right_id += '#'+(makeQuestionView.optionNumber+65).toString()
                            }

                        }
                        //把新加的选项添加到界面上
                        makeQuestionView.selectionLO.addView(optionView)
                        //选项数目加一
                        makeQuestionView.optionNumber += 1
                    }
                    //把控件和变量绑定
                    bindWidgetWithVariable(makeQuestionView)
                    //把新创建的问题添加到界面上
                    addedQList.addView(makeQuestionView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                    questionNumber += 1;
                    group.clearCheck()
                }
                R.id.text_question->{
                    //type == 2 是文本题
                    recordInput.add(initQuestion(2))
                    val makeQuestionView = MakeQuestionView(this,questionNumber,2)
                    val textAnswer = EditText(this)
                    makeQuestionView.selectionLO.addView(textAnswer,ViewGroup.LayoutParams.MATCH_PARENT)
                    //还没有确定要怎么记录答案
                    bindWidgetWithVariable(makeQuestionView)
                    addedQList.addView(makeQuestionView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                    questionNumber += 1;
                    group.clearCheck()
                }
                R.id.inventory_problem->{
                    //type == 3 是量表题
                    val makeQuestionView = MakeQuestionView(this,questionNumber,3)

                    recordInput.add(initQuestion(3))
                    makeQuestionView.addSelectionBT.setOnClickListener {
                        countForInventoryProblem++
                        val optionView = OptionView(this,3,makeQuestionView.optionNumber,false,countForInventoryProblem)
                        bindWidgetWithVariable(makeQuestionView)
                        makeQuestionView.selectionLO.addView(optionView)
                        makeQuestionView.optionNumber += 1
                    }
                    addedQList.addView(makeQuestionView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                    questionNumber += 1;
                    group.clearCheck()
                }
                R.id.sequencing_question->{
                    //type == 4 是顺序题
                    recordInput.add(initQuestion(4))
                    val makeQuestionView = MakeQuestionView(this,questionNumber,4)

                    makeQuestionView.addSelectionBT.setOnClickListener {
                        val optionView = OptionView(this,4,makeQuestionView.optionNumber,false)
                        makeQuestionView.selectionLO.addView(optionView)
                        makeQuestionView.optionNumber += 1
                    }
                    bindWidgetWithVariable(makeQuestionView)
                    addedQList.addView(makeQuestionView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                    questionNumber += 1;
                    group.clearCheck()
                }
            }
        }

        //提交按钮的点击监听
        submitBT.setOnClickListener {
            createExam(TaskModel.paperType,
                TaskModel.paperName,
                TaskModel.paperHint,
                TaskModel.number,
                TaskModel.isRandom,
                toStandard(),
                TaskModel.startTime,
                TaskModel.endTime,
                TaskModel.lastTime,
                TaskModel.password,
                TaskModel.times,



                )
        }
    }
    // 初始化一个问题，一开始只知道type，其他都等待用户输入

    fun bindWidgetWithVariable(makeQuestionView: MakeQuestionView){
        makeQuestionView.apply {
            //MyTextChangedLintener 会根据不同的id把editText的输入值映射给不同的变量
            questionEt.addTextChangedListener(MyTextChangedListener(WigitId.QUESTION, makeQuestionView.questionNumber-1))
            valueET.addTextChangedListener(MyTextChangedListener(WigitId.VALUE, makeQuestionView.questionNumber-1))
            necessaryCB.setOnCheckedChangeListener { buttonView, isChecked ->
                recordInput[makeQuestionView.questionNumber-1].is_necessary = necessaryCB.isChecked
            }
            randomCB.setOnCheckedChangeListener { buttonView, isChecked ->
                recordInput[makeQuestionView.questionNumber-1].is_random = randomCB.isChecked
            }
            needQET.addTextChangedListener(MyTextChangedListener(WigitId.NEED_QUESTION, makeQuestionView.questionNumber-1))
            needAET.addTextChangedListener(MyTextChangedListener(WigitId.NEED_ANSWER, makeQuestionView.questionNumber-1))

        }

    }
}
