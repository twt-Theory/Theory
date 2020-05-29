package com.example.twttheory.mainPage

import com.example.twttheory.exam.PostQuestion

class ChangableData {
    //修改题目
    data class ChangableChangedQuestion(
        var question_id: Int,
        var question: String,
        var answer: List<String>,
        var right_id: String,
        var type: Int,
        var is_necessary: Boolean,
        var is_random: Boolean,
        var score: Int,
        var need_question: Int,
        var need_answer: Int,
        var max_select: Int,
        var min_select: Int
    )

    //"我发布的"问卷
    data class Posted(
        var paper_id: Int,
        var paper_name: String,
        var paper_hint: String,
        var start_time: String,
        var end_time: String,
        var last_time: Int,
        var password: String,
        var times: Int,
        var number: Int,
        var paper_type: Int,
        var is_random: Boolean
    )

    //"和我相关的"问卷
    data class Related(
        var paper_id: Int,
        var paper_name: String,
        var paper_hint: String,
        var start_time: String,
        var end_time: String,
        var last_time: Int,
        var times: Int,
        var number: Int,
        var paper_type: Int,
        var score: Int
    )

    //答题时获取的所有题目
    data class Questions(
        val paper_question: List<GetQuestion>,
        val submit_id: Int
    )

    //答题时获取的单个题目
    data class GetQuestion(
        val question_id: Int,
        val question: String,
        val answer: List<String>,
        val type: Int,
        val is_necessary: Boolean,
        val is_random: Boolean,
        val score: Int,
        val need_question: Int,
        val need_answer: Int,
        val max_select: Int,
        val min_select: Int
    )

    //创建新任务时的每一道题
    data class ChangablePostQuestion(
        var question: String,
        var answer: MutableList<String>,
        var right_id: String,
        var type: Int,
        var is_necessary: Boolean,
        var is_random: Boolean,
        var score: Int,
        var need_question: Int,
        var need_answer: Int,
        var max_select: Int,
        var min_select: Int
    )

    //交卷时的每道题
    data class HandleQuestion(
        val question_id: Int,
        val answer: String
    )
}