package com.example.twttheory.mainPage

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Switch

import com.example.twttheory.R
import com.example.twttheory.exam.changeExam
import com.example.twttheory.service.RefreshState
import kotlinx.android.synthetic.main.fragment_setting.*
import java.sql.Time
import java.util.*
import android.app.DatePickerDialog as DatePickerDialog1

/**
 * A simple [Fragment] subclass.
 */
object SettingFragment : Fragment() {
    private lateinit var titleEt: EditText
    private lateinit var insEt: EditText
    private lateinit var start: EditText
    private lateinit var durationEt: EditText
    private lateinit var timesLimit: EditText
    private lateinit var isHasPW: Switch
    private lateinit var passwordEt: EditText
    private lateinit var isHasLimit: Switch
    private lateinit var needNumber: CheckBox
    private lateinit var majorLimit: CheckBox
    private lateinit var gradeLimit: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_setting, container, false)
//        var title : String      //问卷题目
//        var ins : String        //问卷说明
//        var startDate  : Date       //这里还不知道要怎么写
//        var endDate : Date
//        var startTime  : Time      //这里还不知道要怎么写
//        var endTime : Time
//        var duration : Timer
//        var hasPswd : Boolean   //是否有密码
//        var pswd: String        //密码
//        var isLimited : Boolean //是否有人员限制
//        var needId : Boolean   //是否需要输入学号（开启人员限制才能用）
//        var majorLimit : Boolean  //是否限制专业
//        var gradeLimit : Boolean  //是否限制年级
//        var type : PaperType      //问卷类型（调查问卷、投票、理论答题）
        titleEt = view.findViewById(R.id.title1)
        insEt = view.findViewById(R.id.instruction_input)
        start = view.findViewById<EditText>(R.id.start).apply {
            inputType = InputType.TYPE_NULL
            setOnFocusChangeListener { v, hasFocus ->

            }
        }
        durationEt = view.findViewById(R.id.time_length)
        timesLimit = view.findViewById(R.id.timeLimitEt)
        isHasPW = view.findViewById(R.id.switch1)
        //下面的功能还没实现
        isHasLimit = view.findViewById(R.id.switch2)
        needNumber = view.findViewById(R.id.checkBox)
        majorLimit = view.findViewById(R.id.major_limit)
        gradeLimit = view.findViewById(R.id.checkBox3)
        return view
    }

    //0为正常返回，1~5为缺少输入项，-1为其他问题
    fun changeSettings(paper_id: Int): Int {
        var case: Int = -1
        when {
            titleEt.text.isBlank() -> case = 1
            insEt.text.isBlank() -> case = 2
            durationEt.text.isBlank() -> case = 3
            timesLimit.text.isBlank() -> case = 4
            isHasPW.isChecked && passwordEt.text.isBlank() -> case = 5
            else -> {
                changeExam(
                    paper_id,
                    titleEt.text.toString(),
                    insEt.text.toString(),
                    "",
                    "",
                    durationEt.text.toString(),
                    if (isHasPW.isChecked) passwordEt.text.toString() else "",
                    timesLimit.text.toString()
                ) {
                    when (it) {
                        is RefreshState.Success -> case = 0
                        is RefreshState.Failure -> case = -1
                    }
                }
            }
        }
        return case
    }
}
