package com.example.twttheory.mainPage

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.LayoutAnimationController
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.twttheory.R
import com.example.twttheory.analysis.AnalysisActivity
import com.example.twttheory.exam.ExamActivity
import com.example.twttheory.exam.Related
import com.example.twttheory.manage.ManageActivity
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView

class MainPage : AppCompatActivity() {

    private var fold = true  //"我参与的问卷"列表，0是收起，1是展开
    var finished = false
    var taskNumber = 0     //任务数量
    lateinit var title : TextView
    lateinit var relatedList : MutableList<Related>

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = findViewById(R.id.title)
        val creatTaskBotton : TextView = findViewById(R.id.creat_task)
        val remindCalendar = findViewById<CalendarView>(R.id.calendarView)
        val joinList : LinearLayout = findViewById(R.id.joins)
        val releaseList : LinearLayout = findViewById(R.id.tasks)
        //使用网络请求后，把“与我相关的任务”存到“relatedList”里，此处先暂时设定为临时数据
        val tempRelated = Related(-1,"-1","-1","-1","-1",-1,-1,-1,-1,-1)
        relatedList = arrayListOf(tempRelated,tempRelated,tempRelated)


        for (i in 1 .. 3){
            val joinItemView = JoinItemView(this)
            joinItemView.setOnClickListener{
                val intent = Intent()
                intent.putExtra("paper_id",-1)
                intent.setClass(this@MainPage,ExamActivity::class.java)
                startActivity(intent)
            }
            joinList.addView(joinItemView)

            val releaseItemView = ReleaseItemView(this)
            releaseItemView.setOnClickListener {
                val intent = Intent()
                intent.putExtra("paper_id",-1)
                intent.setClass(this@MainPage,ExamActivity::class.java)
                startActivity(intent)
            }
            releaseItemView.manageBT.setOnClickListener {
                val intent = Intent()
                intent.putExtra("paper_id",-1)
                intent.setClass(this@MainPage,ManageActivity::class.java)
                startActivity(intent)
            }
            releaseItemView.analysisBT.setOnClickListener {
                val intent = Intent()
                intent.putExtra("paper_id",-1)
                intent.setClass(this@MainPage,AnalysisActivity::class.java)
                startActivity(intent)
            }
            releaseList.addView(releaseItemView)
        }


        var mCalendar = Calendar()
        mCalendar.apply {
            year = 2020
            month = 4
            day = 24
        }
        remindCalendar.setSchemeColor(707070,0,0)
        remindCalendar.setSchemeDate(listOf(mCalendar))

        val showMore = findViewById<TextView>(R.id.load)

        //设置动画
        var scaleAnimation : ScaleAnimation = ScaleAnimation(0.1f, 1.0f,0.1f,1.0f)//缩放动画
        scaleAnimation.duration = 500
        scaleAnimation.interpolator = LinearInterpolator()
        val controller  = LayoutAnimationController(scaleAnimation)
        controller.delay = 0.5F//两倍duration间隔
        controller.interpolator = LinearInterpolator()//设置线性插值器


        showMore.setOnClickListener {
            if (fold){
                fold = false
                showMore.text = "点击收起"
            }else{

                fold = true
                showMore.text = "显示更多"
            }
        }
        //创建任务的按钮
        creatTaskBotton.setOnClickListener {
            val intent : Intent = Intent()
            intent.setClass(this@MainPage, CreatTaskActivity::class.java)
            startActivity(intent)
        }
    }


}

