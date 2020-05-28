package com.example.twttheory.mainPage

object TaskModel {
    var recordInput : MutableList<ChangableData.ChangablePostQuestion> = ArrayList()    //出题时记录输入
    var paper_type  : Int = 0
    var questionNumber : Int = 1      //记录题号，每出一题加一
}