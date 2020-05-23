package com.example.twttheory.mainPage

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Build
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
import androidx.annotation.RequiresApi

import com.example.twttheory.R
import com.example.twttheory.exam.*
import com.example.twttheory.service.RefreshState
import kotlinx.android.synthetic.main.fragment_setting.*
import java.sql.Time
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import android.app.DatePickerDialog as DatePickerDialog1

/**
 * A simple [Fragment] subclass.
 */
object SettingFragment : Fragment() {
    private lateinit var titleEt: EditText
    private lateinit var insEt: EditText
    private lateinit var start: EditText
    private lateinit var end: EditText
    private lateinit var durationEt: EditText
    private lateinit var timesLimit: EditText
    private lateinit var isHasPW: Switch
    private lateinit var passwordEt: EditText
    private lateinit var isHasLimit: Switch
    private lateinit var needNumber: CheckBox
    private lateinit var majorLimit: CheckBox
    private lateinit var gradeLimit: CheckBox
    private lateinit var hour: EditText        //做题用时限制
    private lateinit var min: EditText          //做题用时限制
    private lateinit var sec: EditText         //做题用时限制
    lateinit var startDateTime: String
    lateinit var endDateTime: String
    var startDateTimeStamp: Long = 0
    var endDateTimeStamp: Long = 0
    var timeLength: Long = 0

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
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
            setOnClickListener {
                val c = Calendar.getInstance()
                var mYear = c.get(Calendar.YEAR)
                var mMonth = c.get(Calendar.MONTH)
                var mDay = c.get(Calendar.DATE)
                var mHour = c.get(Calendar.HOUR)
                var mMinute = c.get(Calendar.MINUTE)
                //  var mSecond = c.get(Calendar.SECOND)
                val datePicker = DatePickerDialog1(
                    context,
                    DatePickerDialog1.OnDateSetListener { view, year, month, dayOfMonth ->
                        val displayedMonth = month + 1  //实际展示的月份要加一
                        start.hint = "$year-$displayedMonth-$dayOfMonth"
                        val timePicker = TimePickerDialog(
                            context,
                            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                                start.hint =
                                    "$year-$displayedMonth-$dayOfMonth $hourOfDay:$mMinute:00"
                                startDateTime =
                                    "$year-$displayedMonth-$dayOfMonth $hourOfDay:$mMinute:00"
                                startDateTimeStamp = getTimestamp(startDateTime)
                            },
                            mDay,
                            mMinute,
                            true
                        )
                        timePicker.show()
                    },
                    mYear,
                    mMonth,
                    mDay
                )
                datePicker.show()
            }
        }
        end = view.findViewById<EditText>(R.id.end).apply {
            inputType = InputType.TYPE_CLASS_DATETIME
            setOnClickListener {
                val c = Calendar.getInstance()
                var mYear = c.get(Calendar.YEAR)
                var mMonth = c.get(Calendar.MONTH)
                var mDay = c.get(Calendar.DATE)
                var mHour = c.get(Calendar.HOUR)
                var mMinute = c.get(Calendar.MINUTE)
                //  var mSecond = c.get(Calendar.SECOND)
                val datePicker = DatePickerDialog1(
                    context,
                    DatePickerDialog1.OnDateSetListener { view, year, month, dayOfMonth ->
                        val displayedMonth = month + 1  //实际展示的月份要加一
                        end.hint = "$year-$displayedMonth-$dayOfMonth"
                        val timePicker = TimePickerDialog(
                            context,
                            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                                end.hint = "$year-$displayedMonth-$dayOfMonth $hourOfDay-$mMinute"
                                endDateTime =
                                    "$year-$displayedMonth-$dayOfMonth $hourOfDay:$mMinute:00"
                                endDateTimeStamp = getTimestamp(endDateTime)
                            },
                            mDay,
                            mMinute,
                            true
                        )
                        timePicker.show()
                    },
                    mYear,
                    mMonth,
                    mDay
                )
                datePicker.show()

            }
        }
        //     durationEt = view.findViewById(R.id.time_length)
        hour = view.findViewById(R.id.time_length_hour)
        min = view.findViewById(R.id.time_length_minute)
        sec = view.findViewById(R.id.time_length_second)

        timeLength = hour.text.toString().toLong() * 3600 + min.text.toString()
            .toLong() * 60 + sec.text.toString().toLong()

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
                changeLimit(
                    paper_id,
                    titleEt.text.toString(),
                    insEt.text.toString(),
                    startDateTime,   //字符串和时间戳都有，字符串是startDateTime, 时间戳是startDateTimeStamp
                    endDateTime,
                    timeLength.toString(),
                    if (isHasPW.isChecked) passwordEt.text.toString() else "",
                    timesLimit.text.toString()
                ) {
                    case = when (it) {
                        is RefreshState.Success -> 0
                        else -> -1
                    }
                }
            }
        }
        return case
    }

    fun getTimestamp(time: String): Long {
        var timestamp: Long = 0;
        try {
            timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time).getTime();
        } catch (e: ParseException) {
            e.printStackTrace();
        }
        return timestamp;
    }
}
