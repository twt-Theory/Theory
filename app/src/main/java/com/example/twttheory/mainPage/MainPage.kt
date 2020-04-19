package com.example.twttheory.mainPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twttheory.R

class MainPage : AppCompatActivity() {

    lateinit var taskList : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        taskList = findViewById(R.id.tasks)
        var tempDataBean = ArrayList<DataBean>()
        val temp = DataBean("运筹学习题","进行中","2020/4/13 21:00 ~ 21:03")
        tempDataBean.add(temp)
        tempDataBean.add(temp)
        tempDataBean.add(temp)
        val listAdapter = ListAdapter(this,tempDataBean,AdapterType.RELEASE)
        val joinAdapter = ListAdapter(this,tempDataBean,AdapterType.JOIN)
        val recyclerView = findViewById<RecyclerView>(R.id.tasks)
        val joinList = findViewById<RecyclerView>(R.id.jlist)
        joinList.layoutManager = LinearLayoutManager(this)
        joinList.adapter = joinAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = listAdapter;
    }
}
