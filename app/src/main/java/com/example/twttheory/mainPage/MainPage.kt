package com.example.twttheory.mainPage

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.LayoutAnimationController
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twttheory.R
import com.example.twttheory.analysis.AnalysisActivity
import com.example.twttheory.exam.ExamActivity
import com.example.twttheory.manage.ManageActivity
import com.example.twttheory.result.ResultActivity
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import kotlinx.android.synthetic.main.fragment_setting.*

class MainPage : AppCompatActivity() {

    private var fold = true  //"我参与的问卷"列表，0是收起，1是展开
    var finished = false
    var taskNumber = 0     //任务数量
    var resultFragment : Fragment? = null
    lateinit var title : TextView
    lateinit var joinedTasks : ArrayList<View>
    lateinit var taskList : RecyclerView

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        taskList = findViewById(R.id.tasks)
        title = findViewById(R.id.title)
        val creatTaskBotton : TextView = findViewById(R.id.creat_task)
        var tasksOnShow = ArrayList<DataBean>()    //列表显示的问卷
        var allTasksExceptTopTwo = ArrayList<DataBean>()       //实际上所有的问卷
        var myReleasedTasks = ArrayList<DataBean>()         //我发布的问卷
        val tempData = DataBean("大学物理习题","进行中","2020/4/13 21:00 ~ 21:03")
        val remindCalendar = findViewById<CalendarView>(R.id.calendarView)
        var topTwoItems = listOf<DataBean>(tempData,tempData)
        //Fragment manager
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()

        var mCalendar = Calendar()
        mCalendar.apply {
            year = 2020
            month = 4
            day = 24
        }
        remindCalendar.setSchemeColor(707070,0,0)
        remindCalendar.setSchemeDate(listOf(mCalendar))
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
        //设置点击事件
        joinedAdapter.setOnItemClickListener(object : ListAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent()
                intent.putExtra("paper_id",-1)
                intent.setClass(this@MainPage,ExamActivity::class.java)
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
                    intent.putExtra("paper-id",-1)
                }
                startActivity(intent)
            }

            override fun onItemLongClick(view: View, position: Int) {

            }
        })
        releasedAdapter.onManageButtonClickListener = View.OnClickListener {
            val intent = Intent()
            intent.setClass(this@MainPage, ManageActivity::class.java)
            startActivity(intent)
        }
        releasedAdapter.onDataAnalysisBottonClickListener = View.OnClickListener {
            val intent = Intent()
            intent.setClass(this@MainPage, AnalysisActivity::class.java)
            startActivity(intent)
        }
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

        //创建任务的按钮
        creatTaskBotton.setOnClickListener {
            val intent : Intent = Intent()
            intent.setClass(this@MainPage, CreatTaskActivity::class.java)
            startActivity(intent)
        }
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

    private fun hideAllFragment( transaction : FragmentTransaction) {
        if (resultFragment != null && !resultFragment!!.isHidden()) {
            transaction.hide(resultFragment!!);
        }
        
    }
}

