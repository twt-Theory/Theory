package com.example.twttheory.mainPage

import android.Manifest
import android.R
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.twttheory.enums.WigitId
import com.example.twttheory.exam.createExam
import com.example.twttheory.mainPage.TaskModel.paperType
import com.example.twttheory.mainPage.TaskModel.questionNumber
import com.example.twttheory.mainPage.TaskModel.recordInput
import com.example.twttheory.mainPage.TaskModel.toStandard
import com.example.twttheory.mainPage.UsefulFunctions.initQuestion
import com.example.twttheory.service.RefreshState
import com.example.twttheory.utils.ExcelUtils
import com.example.twttheory.views.MakeQuestionView
import com.example.twttheory.views.OptionView
import com.study.fileselectlibrary.AllFileActivity
import com.study.fileselectlibrary.bean.FileItem
import com.study.fileselectlibrary.utils.PermissionCheckUtils
import java.io.File


class CreatTaskActivity : AppCompatActivity() {
    lateinit var radioButtons : MutableList<MutableList<RadioButton>>
    lateinit var uploadBT : Button
    lateinit var addedQList : LinearLayout
    lateinit var testTV : TextView

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.twttheory.R.layout.activity_creat_task)

        val settingButton : Button = findViewById(com.example.twttheory.R.id.settings)
        var settingFragment : Fragment? = null
        var isSettingVisible = false;
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val returnButton = findViewById<ImageView>(com.example.twttheory.R.id.return_button)
        val typeChoose: RadioGroup = findViewById<RadioGroup>(com.example.twttheory.R.id.choose_type)

        val releaseBT = findViewById<Button>(com.example.twttheory.R.id.release)
        val paperTypeRG = findViewById<RadioGroup>(com.example.twttheory.R.id.type)
        //用来数量表题是应该加线还是加选项
        var countForInventoryProblem : Int = 0
        //存储radioButtons便于控制
        radioButtons = ArrayList()
        addedQList = findViewById<LinearLayout>(com.example.twttheory.R.id.questions)
        testTV = findViewById<TextView>(com.example.twttheory.R.id.test)
        //清空TaskModel的列表
        TaskModel.recordInput.clear()
        //设置批量上传按钮的点击监听事件
        uploadBT = findViewById<Button>(com.example.twttheory.R.id.bulk_import)
        uploadBT.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT);
            //intent.setType(“image/*”);//选择图片        //之后还会用到选择图片（出题时用图片当作题目和选项）
            //intent.setType(“audio/*”); //选择音频
            //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
            //intent.setType(“video/*;image/*”);//同时选择视频和图片
            intent.setType("*/*");//无类型限制
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 1);
        }

        //打开文件选择器前提醒设置权限
        PermissionCheckUtils.setOnOnWantToOpenPermissionListener {
            Toast.makeText(this, "请去设置的应用管理中打开应用读取内存的权限", Toast.LENGTH_SHORT).show();
        }

        //选择问卷类型的radio group 的监听事件
        paperTypeRG.setOnCheckedChangeListener{group, checkedId ->
            when(checkedId){
                com.example.twttheory.R.id.questionare -> {
                    paperType = 0
                }
                com.example.twttheory.R.id.vote ->{
                    paperType = 1
                }
                com.example.twttheory.R.id.exam->{
                    paperType = 2
                }
            }

        }
        //返回按钮的点击监听
        returnButton.setOnClickListener {
            this.finish()
        }
        //显示设置权限fragment
        settingFragment = SettingFragment
        mFragmentTransaction.add(com.example.twttheory.R.id.setting,settingFragment!!)
        mFragmentTransaction.commit()
        //设置权限按钮的点击监听
        settingButton.setOnClickListener {
            if (!isSettingVisible){

                mFragmentTransaction.show(settingFragment!!)

                isSettingVisible = true
            }else{
                mFragmentTransaction.hide(settingFragment!!)
                isSettingVisible = false
            }
        }
        //题目类型选择的radio group的监听
        typeChoose.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                com.example.twttheory.R.id.single_selection ->{
                    //type == 0 是单选题
                    recordInput.add(initQuestion(0))
                    radioButtons.add(ArrayList())
                    val makeQuestionView =
                        MakeQuestionView(
                            this,
                            questionNumber,
                            0
                        )
                    makeQuestionView.addSelectionBT.setOnClickListener {
                        val optionView = OptionView(
                            this,
                            0,
                            makeQuestionView.optionNumber,
                            false
                        )
                        radioButtons[makeQuestionView.questionNumber-1].add(optionView.optionSingle)
                        recordInput[makeQuestionView.questionNumber-1].answer.add("-1")
                        val listener = TextChangedListener(WigitId.OPTION_CONTENT, makeQuestionView.questionNumber-1)
                        listener.optionNum =  makeQuestionView.optionNumber
                        optionView.optionContent.addTextChangedListener(listener)
//                        if (optionView.optionContent.text.toString()!="")
//                            recordInput[makeQuestionView.questionNumber-1].answer[makeQuestionView.optionNumber] = optionView.optionContent.text.toString()
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
                com.example.twttheory.R.id.multiple_selection->{
                    //type == 1 是多选题
                    recordInput.add(initQuestion(1))
                    radioButtons.add(ArrayList())
                    val makeQuestionView =
                        MakeQuestionView(
                            this,
                            questionNumber,
                            1
                        )
                    makeQuestionView.addSelectionBT.setOnClickListener {
                        val optionView = OptionView(
                            this,
                            1,
                            makeQuestionView.optionNumber,
                            false
                        )
                        recordInput[makeQuestionView.questionNumber-1].answer.add("-1")
                        val listener = TextChangedListener(WigitId.OPTION_CONTENT, makeQuestionView.questionNumber-1)
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
                com.example.twttheory.R.id.text_question->{
                    //type == 2 是文本题
                    recordInput.add(initQuestion(2))
                    val makeQuestionView =
                        MakeQuestionView(
                            this,
                            questionNumber,
                            2
                        )
                    val textAnswer = EditText(this)
                    makeQuestionView.selectionLO.addView(textAnswer,ViewGroup.LayoutParams.MATCH_PARENT)
                    //还没有确定要怎么记录答案
                    bindWidgetWithVariable(makeQuestionView)
                    addedQList.addView(makeQuestionView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                    questionNumber += 1;
                    group.clearCheck()
                }
                com.example.twttheory.R.id.inventory_problem->{
                    //type == 3 是量表题
                    val makeQuestionView =
                        MakeQuestionView(
                            this,
                            questionNumber,
                            3
                        )

                    recordInput.add(initQuestion(3))
                    makeQuestionView.addSelectionBT.setOnClickListener {
                        countForInventoryProblem++
                        val optionView = OptionView(
                            this,
                            3,
                            makeQuestionView.optionNumber,
                            false,
                            countForInventoryProblem
                        )
                        bindWidgetWithVariable(makeQuestionView)
                        makeQuestionView.selectionLO.addView(optionView)
                        makeQuestionView.optionNumber += 1
                    }
                    addedQList.addView(makeQuestionView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                    questionNumber += 1;
                    group.clearCheck()
                }
                com.example.twttheory.R.id.sequencing_question->{
                    //type == 4 是顺序题
                    recordInput.add(initQuestion(4))
                    val makeQuestionView =
                        MakeQuestionView(
                            this,
                            questionNumber,
                            4
                        )

                    makeQuestionView.addSelectionBT.setOnClickListener {
                        val optionView = OptionView(
                            this,
                            4,
                            makeQuestionView.optionNumber,
                            false
                        )
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
        releaseBT.setOnClickListener {
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
                TaskModel.times
                ){
                when (it) {
                    is RefreshState.Success -> {
                        Toast.makeText(this,"创建问卷成功",Toast.LENGTH_SHORT)
                        this.finish()
                    }
                    else -> Toast.makeText(this,"创建问卷失败，请检查网络配置",Toast.LENGTH_SHORT)
                }
            }
        }
    }
    // 初始化一个问题，一开始只知道type，其他都等待用户输入

    fun bindWidgetWithVariable(makeQuestionView: MakeQuestionView){
        makeQuestionView.apply {
            //MyTextChangedLintener 会根据不同的id把editText的输入值映射给不同的变量
            questionEt.addTextChangedListener(TextChangedListener(WigitId.QUESTION, makeQuestionView.questionNumber-1))
            valueET.addTextChangedListener(TextChangedListener(WigitId.VALUE, makeQuestionView.questionNumber-1))
            necessaryCB.setOnCheckedChangeListener { buttonView, isChecked ->
                recordInput[makeQuestionView.questionNumber-1].is_necessary = necessaryCB.isChecked
            }
            randomCB.setOnCheckedChangeListener { buttonView, isChecked ->
                recordInput[makeQuestionView.questionNumber-1].is_random = randomCB.isChecked
            }
            needQET.addTextChangedListener(TextChangedListener(WigitId.NEED_QUESTION, makeQuestionView.questionNumber-1))
            needAET.addTextChangedListener(TextChangedListener(WigitId.NEED_ANSWER, makeQuestionView.questionNumber-1))

        }

    }


    fun file(view : View){
        val size = PermissionCheckUtils.checkActivityPermissions(
            this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            100, null
        )
        if (size == 0) {
            jumpActivity()
        }

    }
    private fun jumpActivity() {
        val intent = Intent(this, AllFileActivity::class.java)
        startActivityForResult(intent, 200)
        overridePendingTransition(com.study.fileselectlibrary.R.anim.enter, com.study.fileselectlibrary.R.anim.exit)
    }
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String?>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            var flag = true
            for (i in permissions.indices) {
                flag = flag and (grantResults[i] == PackageManager.PERMISSION_GRANTED)
            }
            if (flag) {
                jumpActivity()
            }
        }
    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 200) {
            if (resultCode == 200) {
                val resultFileList: ArrayList<FileItem>? =
                    data.getParcelableArrayListExtra<FileItem>("file")
                if (resultFileList != null && resultFileList.size > 0) {
                    TaskModel.sheetResult = ExcelUtils.xlsJxl(File(resultFileList[0].path))
                    testTV.text = TaskModel.sheetResult.toString()

                }
            }
        }
    }


}
