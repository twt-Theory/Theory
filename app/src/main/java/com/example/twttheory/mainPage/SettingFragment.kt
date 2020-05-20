package com.example.twttheory.mainPage

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText

import com.example.twttheory.R
import kotlinx.android.synthetic.main.fragment_setting.*
import java.sql.Time
import java.util.*
import android.app.DatePickerDialog as DatePickerDialog1

/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_setting,container,false)
        var title : String      //问卷题目
        var ins : String        //问卷说明
        var startDate  : Date       //这里还不知道要怎么写
        var endDate : Date
        var startTime  : Time      //这里还不知道要怎么写
        var endTime : Time
        var duration : Timer
        var hasPswd : Boolean   //是否有密码
        var pswd: String        //密码
        var isLimited : Boolean //是否有人员限制
        var needId : Boolean   //是否需要输入学号（开启人员限制才能用）
        var majorLimit : Boolean  //是否限制专业
        var gradeLimit : Boolean  //是否限制年级
        var type : PaperType      //问卷类型（调查问卷、投票、理论答题）
        val titleIn : EditText = view.findViewById(R.id.title1)
        val insIn : EditText = view.findViewById(R.id.instruction_input)
        val start: EditText = view.findViewById(R.id.start)
        title = titleIn.text.toString()
        ins = insIn.text.toString()
        start.inputType = InputType.TYPE_NULL
        start.setOnFocusChangeListener { v, hasFocus ->
           // showDatePickerDialog()
        }
        return view
    }



}
