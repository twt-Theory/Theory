package com.example.twttheory.mainPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.LayoutAnimationController
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twttheory.R
import com.example.twttheory.exam.ExamActivity
import com.example.twttheory.result.ResultActivity

class MainPage : AppCompatActivity() {

    private var fold = true  //"我参与的问卷"列表，0是收起，1是展开
    var finished = false
    var taskNumber = 0     //任务数量
    lateinit var joinedTasks : ArrayList<View>
    lateinit var taskList : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        taskList = findViewById(R.id.tasks)
        var tasksOnShow = ArrayList<DataBean>()    //列表显示的问卷
        var allTasksExceptTopTwo = ArrayList<DataBean>()       //实际上所有的问卷
        var myReleasedTasks = ArrayList<DataBean>()         //我发布的问卷
        val tempData = DataBean("大学物理习题","进行中","2020/4/13 21:00 ~ 21:03")
        var topTwoItems = listOf<DataBean>(tempData,tempData)
        //前两个任务单独显示
        tasksOnShow.add(tempData)
        tasksOnShow.add(tempData)
        //除了前两个任务外的所有任务
        allTasksExceptTopTwo.add(DataBean("高等数学习题","进行中","2020/4/13 21:00 ~ 21:03"))
        allTasksExceptTopTwo.add(DataBean("离散数学习题","进行中","2020/4/13 21:00 ~ 21:03"))
        //“我发布的任务”暂时赋值为tasksOnShow
        myReleasedTasks = tasksOnShow
        //初始化
        val joinedAdapter = ListAdapter(this,tasksOnShow,AdapterType.JOIN)
        joinedAdapter.setOnItemClickListener(object : ListAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent()
                intent.setClass(this@MainPage,ResultActivity::class.java)
                startActivity(intent)
            }

            override fun onItemLongClick(view: View, position: Int) {

            }
        })
        val releasedAdapter = ListAdapter(this,myReleasedTasks,AdapterType.RELEASE)
        releasedAdapter.setOnItemClickListener(object : ListAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent()
                if (finished){
                    intent.setClass(this@MainPage,ResultActivity::class.java)
                }else{
                    intent.setClass(this@MainPage, ExamActivity::class.java)
                }

                startActivity(intent)
            }

            override fun onItemLongClick(view: View, position: Int) {

            }
        })
        val releasedList = findViewById<RecyclerView>(R.id.tasks)
        val joinedList = findViewById<RecyclerView>(R.id.joinedlist)
        val showMore = findViewById<TextView>(R.id.load)
        //recycler view 设置 layoutManager 和 adapter
        releasedList.layoutManager = LinearLayoutManager(this)
        releasedList.adapter = releasedAdapter

        joinedList.layoutManager = LinearLayoutManager(this)
        joinedList.adapter = joinedAdapter

        //设置动画
        var scaleAnimation : ScaleAnimation = ScaleAnimation(0.1f, 1.0f,0.1f,1.0f)//缩放动画
        scaleAnimation.duration = 500
        scaleAnimation.interpolator = LinearInterpolator()
        val controller  = LayoutAnimationController(scaleAnimation)
        controller.delay = 0.5F//两倍duration间隔
        controller.interpolator = LinearInterpolator()//设置线性插值器
        releasedList.layoutAnimation = controller//设置动画
        joinedList.layoutAnimation = controller

        showMore.setOnClickListener {
            if (fold){
                for(i in 0 .. allTasksExceptTopTwo.size-1){
                    joinedAdapter.notifyItemInserted(i+2)
                    tasksOnShow.add(allTasksExceptTopTwo[i])
                    joinedAdapter.notifyDataSetChanged()
                }
                fold = false
                showMore.text = "点击收起"
            }else{
                tasksOnShow.removeAll(allTasksExceptTopTwo)
                joinedAdapter.notifyDataSetChanged()
                fold = true
                showMore.text = "显示更多"
            }
        }
        //初始状态下隐藏recycler view
    }

    fun foldOrExtendTask(){
        if (taskNumber<=2){
            val t = Toast.makeText(this,"没有更多任务了", Toast.LENGTH_LONG)
            t.show()
        }else{
            for (i in 3..taskNumber){
                if(fold){

                }

            }
        }
    }
}

