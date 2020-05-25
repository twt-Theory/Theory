package com.example.twttheory.mainPage

data class QuestionItem(
    var question: String = "-1",
    var answer: List<String> = listOf("-1"),
    var right_id: String = "-1",
    var type: Int = 0,
    var is_necessary: Boolean = true,
    var is_random: Boolean = false,
    var score: Int = -1,
    var need_question: Int = -1,
    var need_answer: Int = -1,
    var max_select: Int = -1,
    var min_select: Int = -1
)