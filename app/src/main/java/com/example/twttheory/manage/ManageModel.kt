package com.example.twttheory.manage

import com.example.twttheory.exam.ChangedQuestion
import com.example.twttheory.mainPage.ChangableData

object ManageModel {
    var changedList : MutableList<ChangableData.ChangableChangedQuestion> = ArrayList()
    fun initQuestion( type : Int ) = ChangableData.ChangableChangedQuestion(
        -1,
        "-1",
        ArrayList(),
        "-1",
        type,
        true,
        false,
        1,
        -1,
        -1,
        -1,
        -1,
        false
    )
}