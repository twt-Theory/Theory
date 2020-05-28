package com.example.twttheory.mainPage

import android.text.Editable
import android.text.TextWatcher
import com.example.twttheory.exam.ChangedQuestion
import java.text.ParsePosition

class MyTextChangedListener(widgitId : WigitId, position: Int) : TextWatcher {
    var widgitId : WigitId
    var position : Int
    var optionNum : Int = 0
    init {
        this.widgitId = widgitId
        this.position = position
    }
    override fun afterTextChanged(s: Editable?) {
        TaskModel.recordInput[position].apply {
            when(widgitId){
                WigitId.QUESTION->{
                    question = s.toString()
                }
                WigitId.VALUE->{
                    score = s.toString().toInt()
                }
                WigitId.NEED_QUESTION->{
                    need_question = s.toString().toInt()
                }
                WigitId.NEED_ANSWER->{
                    need_answer = s.toString().toInt()
                }
                WigitId.OPTION_CONTENT->{

                }

            }


        }

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }
}