package com.example.twttheory.mainPage

import com.example.twttheory.exam.PostQuestion

object TaskModel {
    var recordInput : MutableList<ChangableData.ChangablePostQuestion> = ArrayList()    //出题时记录输入
    var paper_type  : Int = 0
    var questionNumber : Int = 1      //记录题号，每出一题加一
    var paperType: Int = -1
    var paperName: String = "-1"
    var paperHint: String = "-1"
    var number: Int = -1
    var isRandom: Boolean = false
    var startTime: Long = 0
    var endTime: Long = 0
    var lastTime: Int = 0
    var password: String = "-1"
    var times: Int = 5
    var screenWidth : Int = 0
    var screenHeight : Int = 0
    lateinit var sheetResult : Map<String,List<List<String>>>
    fun toStandard() : MutableList<PostQuestion>{
        val postQuestionList = ArrayList<PostQuestion>()
        recordInput.forEach {
            postQuestionList.add(
                PostQuestion(
                it.question,
                it.answer,
                it.right_id,
                it.type,
                if (it.is_necessary) 1 else 0,
                if (it.is_random)1 else 0,
                it.score,
                it.need_question,
                it.need_answer,
                it.max_select,
                it.min_select)
            )
        }
        return postQuestionList
    }

}
