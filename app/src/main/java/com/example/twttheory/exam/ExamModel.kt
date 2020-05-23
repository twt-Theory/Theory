package com.example.twttheory.exam

object ExamModel {
    var myPosted = mutableListOf<Posted>()
    var myRelated = mutableListOf<Related>()
    var myQuestions = mutableListOf<GetQuestion>()
    var myChangedQuestion = mutableListOf<ChangedQuestion>()

    fun updatePosted(list: List<Posted>) {
        myPosted = list.toMutableList()
        //TODO: here!!!!!!!!!!!!!!!!
    }

    fun updateRelated(list: List<Related>) {
        myRelated = list.toMutableList()
        //TODO: here!!!!!!!!!!!!!!!!
    }

    fun updateQuestions(list: List<GetQuestion>) {
        myQuestions = list.toMutableList()
        //TODO: here!!!!!!!!!!!!!!!!
    }

    fun updateChangedQuestion(list: List<ChangedQuestion>) {
        myChangedQuestion = list.toMutableList()
        //TODO: here!!!!!!!!!!!!!!!!
    }
}