package com.example.twttheory.views

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.twttheory.R

class ReleaseItemView : ConstraintLayout {
    lateinit var titleTV : TextView
    lateinit var timeTV : TextView    //截止时间
    lateinit var manageBT : Button
    lateinit var analysisBT : Button
    constructor(context: Context) : super(context) {
        View.inflate(context, R.layout.item_task,this)
        titleTV = findViewById(R.id.title)
        timeTV  = findViewById(R.id.time)
        manageBT = findViewById(R.id.manage)
        analysisBT = findViewById(R.id.analysis)
    }
}