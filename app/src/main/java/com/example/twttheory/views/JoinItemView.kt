package com.example.twttheory.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.getSystemService
import com.example.twttheory.R

class JoinItemView : ConstraintLayout {
    lateinit var titleTV : TextView
    lateinit var timeTV : TextView    //截止时间
    lateinit var timesTV : TextView  //剩余次数

    constructor(context: Context) : super(context){
        View.inflate(context,R.layout.item_join,this)
        titleTV = findViewById(R.id.title)
        timeTV  = findViewById(R.id.time)
        timesTV = findViewById(R.id.timesLimit)

        titleTV.text = "运筹学习题"
        timeTV.text  = "截止时间：2020-5-24 10:00"
        timesTV.text = "剩余次数：1"
    }
}