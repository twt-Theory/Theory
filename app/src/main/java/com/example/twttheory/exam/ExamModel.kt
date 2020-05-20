package com.example.twttheory.exam

object ExamModel {
    var myPosted = mutableListOf<Posted>()
    var myRelated = mutableListOf<Related>()
    var myQuestions = mutableListOf<GetQuestion>()

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
}