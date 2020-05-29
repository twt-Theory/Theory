package com.example.twttheory.mainPage

import android.text.Editable
import android.text.TextWatcher
import com.example.twttheory.enums.WigitId
import com.example.twttheory.mainPage.TaskModel.paperName

class TextChangedListener(widgitId : WigitId, position: Int) : TextWatcher {
    var widgitId : WigitId
    var position : Int
    var optionNum : Int = 0
    init {
        this.widgitId = widgitId
        this.position = position
    }
    override fun afterTextChanged(s: Editable?) {
        if (widgitId == WigitId.PAPER_TITLE){
            if (s.toString()!="")
                paperName = s.toString()
        }
        else{
            TaskModel.recordInput[position].apply {
                when(widgitId){
                    WigitId.QUESTION->{
                        if (s.toString()!="")
                            question = s.toString()
                    }
                    WigitId.VALUE->{
                        if (s.toString()!="")
                            score = s.toString().toInt()
                    }
                    WigitId.NEED_QUESTION->{
                        if (s.toString()!="")
                            need_question = s.toString().toInt()
                    }
                    WigitId.NEED_ANSWER->{
                        if (s.toString()!="")
                            need_answer = s.toString().toInt()
                    }
                    WigitId.OPTION_CONTENT->{
                        if (s.toString()!="")
                            answer[optionNum] = s.toString()

                    }

                }


            }

        }


    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }
}