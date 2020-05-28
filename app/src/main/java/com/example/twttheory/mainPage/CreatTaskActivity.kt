package com.example.twttheory.mainPage

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.twttheory.R
import com.example.twttheory.exam.PostQuestion
import com.example.twttheory.mainPage.TaskModel.questionNumber
import com.example.twttheory.mainPage.TaskModel.recordInput
import com.example.twttheory.mainPage.UsefulFunctions.initQuestion

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

        var countForInventoryProblem : Int = 0                //用来数量表题是应该加线还是加选项

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
                    recordInput.add(initQuestion(0))
                    val makeQuestionView = MakeQuestionView(this,questionNumber,0)
                    makeQuestionView.addSelectionBT.setOnClickListener {
                        val optionView = OptionView(this,0,makeQuestionView.optionNumber,false)
                        val listener = MyTextChangedListener(WigitId.OPTION_CONTENT, questionNumber-1)
                        listener.optionNum =  makeQuestionView.optionNumber
                        optionView.optionContent.addTextChangedListener(listener)
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
                    val makeQuestionView = MakeQuestionView(this,questionNumber,1)
                    makeQuestionView.addSelectionBT.setOnClickListener {
                        val optionView = OptionView(this,1,makeQuestionView.optionNumber,false)
                        val listener = MyTextChangedListener(WigitId.OPTION_CONTENT, questionNumber-1)
                        listener.optionNum =  makeQuestionView.optionNumber
                        optionView.optionContent.addTextChangedListener(listener)
                        makeQuestionView.selectionLO.addView(optionView)
                        makeQuestionView.optionNumber += 1
                    }
                    bindWidgetWithVariable(makeQuestionView)
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
    }
    // 初始化一个问题，一开始只知道type，其他都等待用户输入

    fun bindWidgetWithVariable(makeQuestionView: MakeQuestionView){
        makeQuestionView.apply {
            //MyTextChangedLintener 会根据不同的id把editText的输入值映射给不同的变量
            questionEt.addTextChangedListener(MyTextChangedListener(WigitId.QUESTION, questionNumber-1))
            valueET.addTextChangedListener(MyTextChangedListener(WigitId.VALUE, questionNumber-1))
            necessaryCB.setOnCheckedChangeListener { buttonView, isChecked ->
                recordInput[questionNumber-1].is_necessary = necessaryCB.isChecked
            }
            randomCB.setOnCheckedChangeListener { buttonView, isChecked ->
                recordInput[questionNumber-1].is_random = randomCB.isChecked
            }
            needQET.addTextChangedListener(MyTextChangedListener(WigitId.NEED_QUESTION, questionNumber-1))
            needAET.addTextChangedListener(MyTextChangedListener(WigitId.NEED_ANSWER, questionNumber-1))

        }

    }
}
