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
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.RequiresApi

import com.example.twttheory.R
import com.example.twttheory.enums.WigitId
import com.example.twttheory.exam.*
import com.example.twttheory.mainPage.TaskModel.endTime
import com.example.twttheory.mainPage.TaskModel.lastTime
import com.example.twttheory.mainPage.TaskModel.paperHint
import com.example.twttheory.mainPage.TaskModel.password
import com.example.twttheory.mainPage.TaskModel.startTime
import com.example.twttheory.mainPage.TaskModel.times
import com.example.twttheory.service.RefreshState
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
    private lateinit var timesLimit: EditText
    private lateinit var isRandomSw: Switch
    private lateinit var numberEt: EditText
    private lateinit var isHasPW: Switch
    private lateinit var passwordEt: EditText
    private lateinit var isHasLimit: Switch
    private lateinit var needNumber: CheckBox
    private lateinit var majorLimit: CheckBox
    private lateinit var gradeLimit: CheckBox
    private lateinit var hour: EditText        //做题用时限制
    private lateinit var min: EditText          //做题用时限制
    private lateinit var sec: EditText         //做题用时限制
    private lateinit var startDateTime: String
    private lateinit var endDateTime: String
    private var startDateTimeStamp: Long = 0
    private var endDateTimeStamp: Long = 0
    private var timeLength: Int = 0

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_setting, container, false)
        val passwordTV : TextView = view.findViewById(R.id.passWordTV)
        titleEt = view.findViewById(R.id.title1)
        insEt = view.findViewById(R.id.instruction_input)
        passwordEt = view.findViewById(R.id.pswd)
        timesLimit = view.findViewById<EditText>(R.id.timeLimitEt)

        passwordEt.visibility = View.INVISIBLE

        isHasPW = view.findViewById(R.id.hasPassword)
        isHasPW.setOnClickListener {
            if (passwordEt.visibility == View.INVISIBLE){
                passwordEt.visibility = View.VISIBLE

            }else{
                passwordEt.visibility = View.INVISIBLE

            }
        }
        start = view.findViewById<EditText>(R.id.start).apply {
            inputType = InputType.TYPE_NULL
            setOnClickListener {
                val c = Calendar.getInstance()
                val mYear = c.get(Calendar.YEAR)
                val mMonth = c.get(Calendar.MONTH)
                val mDay = c.get(Calendar.DATE)
//                val mHour = c.get(Calendar.HOUR)
                val mMinute = c.get(Calendar.MINUTE)
                //  val mSecond = c.get(Calendar.SECOND)
                val datePicker = DatePickerDialog1(
                    context,
                    DatePickerDialog1.OnDateSetListener { _, year, month, dayOfMonth ->
                        val displayedMonth = month + 1  //实际展示的月份要加一
                        start.hint = "$year-$displayedMonth-$dayOfMonth"
                        val timePicker = TimePickerDialog(
                            context,
                            TimePickerDialog.OnTimeSetListener { _, hourOfDay, _ ->
                                start.hint =
                                    "$year-$displayedMonth-$dayOfMonth $hourOfDay:$mMinute:00"
                                startDateTime =
                                    "$year-$displayedMonth-$dayOfMonth $hourOfDay:$mMinute:00"
                                startDateTimeStamp = getTimestamp(startDateTime)
                                startTime = startDateTime               //同步到Model
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
                val mYear = c.get(Calendar.YEAR)
                val mMonth = c.get(Calendar.MONTH)
                val mDay = c.get(Calendar.DATE)
//                val mHour = c.get(Calendar.HOUR)
                val mMinute = c.get(Calendar.MINUTE)
                //  val mSecond = c.get(Calendar.SECOND)
                val datePicker = DatePickerDialog1(
                    context,
                    DatePickerDialog1.OnDateSetListener { _, year, month, dayOfMonth ->
                        val displayedMonth = month + 1  //实际展示的月份要加一
                        end.hint = "$year-$displayedMonth-$dayOfMonth"
                        val timePicker = TimePickerDialog(
                            context,
                            TimePickerDialog.OnTimeSetListener { _, hourOfDay, _ ->
                                end.hint = "$year-$displayedMonth-$dayOfMonth $hourOfDay:$mMinute:00"
                                endDateTime =
                                    "$year-$displayedMonth-$dayOfMonth $hourOfDay:$mMinute:00"
                                endDateTimeStamp = getTimestamp(endDateTime)
                                endTime   = endDateTime       //同步到Model
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


        timeLength = hour.text.toString().toInt() * 60 + min.text.toString()
            .toInt()

        timesLimit = view.findViewById(R.id.timeLimitEt)
        isRandomSw = view.findViewById(R.id.randomSwitch)
        numberEt = view.findViewById(R.id.numberEt)
        isHasPW = view.findViewById(R.id.hasPassword)
        passwordEt = view.findViewById(R.id.pswd)
        //下面的功能还没实现
        isHasLimit = view.findViewById(R.id.switch2)
        needNumber = view.findViewById(R.id.checkBox)
        majorLimit = view.findViewById(R.id.major_limit)
        gradeLimit = view.findViewById(R.id.checkBox3)

        titleEt.addTextChangedListener(TextChangedListener(WigitId.PAPER_TITLE,0))
        paperHint = insEt.text.toString()
        lastTime  = timeLength
        if (timesLimit.text.toString() != "")
        times     = timesLimit.text.toString().toInt()
        if (passwordEt.text.toString() != "")
        password  = passwordEt.toString()
        return view
    }


    //0为正常返回，1~5为缺少输入项，-1为其他问题
    fun changeSettings(paper_id: Int): Int {
        var case: Int = -1
        when {
            titleEt.text.isBlank() -> case = 1
            insEt.text.isBlank() -> case = 2
//            durationEt.text.isBlank() -> case = 3
            timesLimit.text.isBlank() -> case = 4
            isHasPW.isChecked && passwordEt.text.isBlank() -> case = 5
            else -> {
                changeLimit(
                    paper_id,
                    titleEt.text.toString(),
                    insEt.text.toString(),
                    startDateTime,   //字符串和时间戳都有，字符串是startDateTime, 时间戳是startDateTimeStamp
                    endDateTime,
                    timeLength,
                    if (isHasPW.isChecked) passwordEt.text.toString() else "",
                    timesLimit.text.toString().toInt(),
                    numberEt.text.toString().toInt(),
                    isRandomSw.isChecked
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

    private fun getTimestamp(time: String): Long {
        var timestamp: Long = 0
        try {
            timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timestamp
    }
}
