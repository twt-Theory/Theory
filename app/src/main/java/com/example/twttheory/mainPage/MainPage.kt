package com.example.twttheory.mainPage

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.view.animation.LayoutAnimationController
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.twttheory.R
import com.example.twttheory.analysis.AnalysisActivity
import com.example.twttheory.exam.*
import com.example.twttheory.manage.ManageActivity
import com.example.twttheory.service.RefreshState
import com.example.twttheory.views.JoinItemView
import com.example.twttheory.views.ReleaseItemView
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView


class MainPage : AppCompatActivity() {

    private var fold = true  //"我参与的问卷"列表，0是收起，1是展开
    var finished = false
    var taskNumber = 0     //任务数量
    lateinit var title : TextView
    lateinit var relatedList : MutableList<Related>
    lateinit var postedList : MutableList<Posted>
    lateinit var myselfImageView: ImageView

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = findViewById(R.id.title)

        val creatTaskBotton : Button = findViewById(R.id.creat_task)
        val remindCalendar = findViewById<CalendarView>(R.id.calendarView)
        val joinLayout : LinearLayout = findViewById(R.id.joins)
        val releaseLayout : LinearLayout = findViewById(R.id.tasks)
        //获取屏幕宽高
        val defaultDisplay = windowManager.defaultDisplay
        val point = Point()
        defaultDisplay.getSize(point)
        val x: Int = point.x
        val y: Int = point.y
        TaskModel.screenHeight = x
        TaskModel.screenHeight = y

        login(301924433){
            when(it){
                is RefreshState.Success ->{
                    Toast.makeText(this,"登陆成功", Toast.LENGTH_SHORT).show()
                    //获取“与我相关的问卷”
                    getRelatedExam {
                        when (it) {
                            is RefreshState.Success -> {
                                Toast.makeText(this,"获取问卷成功", Toast.LENGTH_SHORT).show()
                                relatedList = ExamModel.myRelated
                                for (i in 0 until relatedList.size){
                                    val joinItemView = JoinItemView(this)
                                    joinItemView.setOnClickListener{
                                        val intent = Intent()
                                        intent.putExtra("paper_id",relatedList[i].paper_id)
                                        intent.setClass(this@MainPage,ExamActivity::class.java)
                                        startActivity(intent)
                                    }
                                    joinLayout.addView(joinItemView)
                                }

                            }
                            else -> Toast.makeText(this,"获取问卷失败，请检查网络配置", Toast.LENGTH_SHORT).show()
                        }
                    }
                    //获取“我发布的问卷”
                    getPostedExam {
                        when (it) {
                            is RefreshState.Success -> {
                                postedList = ExamModel.myPosted
                                for (i in 0 until postedList.size){
                                    val releaseItemView = ReleaseItemView(this)
                                    releaseItemView.setOnClickListener{
                                        val intent = Intent()
                                        intent.putExtra("paper_id",postedList[i].paper_id)
                                        intent.setClass(this@MainPage,ManageActivity::class.java)
                                        startActivity(intent)
                                    }
                                    joinLayout.addView(releaseItemView)
                                }

                            }
                            else -> Toast.makeText(this,"获取问卷失败，请检查网络配置", Toast.LENGTH_SHORT).show()
                        }
                    }





                }
                else ->{
                    Toast.makeText(this,"登陆失败", Toast.LENGTH_SHORT).show()
                }
            }
        }


        //使用网络请求后，把“与我相关的任务”存到“relatedList”里，此处先暂时设定为临时数据
        val tempRelated = Related(-1,"-1","-1","-1","-1",-1,-1,-1,-1,-1)

        //点击头像跳出一个小菜单
        myselfImageView = findViewById(R.id.me)
        myselfImageView.setOnClickListener {
            openContextMenu(myselfImageView)
        }

//        for (i in 1 .. 3){
////            val joinItemView = JoinItemView(this)
////            joinItemView.setOnClickListener{
////                val intent = Intent()
////                intent.putExtra("paper_id",-1)
////                intent.setClass(this@MainPage,ExamActivity::class.java)
////                startActivity(intent)
////            }
////            joinList.addView(joinItemView)
//
//            val releaseItemView =
//                ReleaseItemView(this)
//            releaseItemView.setOnClickListener {
//                val intent = Intent()
//                intent.putExtra("paper_id",-1)
//                intent.setClass(this@MainPage,ExamActivity::class.java)
//                startActivity(intent)
//            }
//            releaseItemView.manageBT.setOnClickListener {
//                val intent = Intent()
//                intent.putExtra("paper_id",-1)
//                intent.setClass(this@MainPage,ManageActivity::class.java)
//                startActivity(intent)
//            }
//            releaseItemView.analysisBT.setOnClickListener {
//                val intent = Intent()
//                intent.putExtra("paper_id",-1)
//                intent.setClass(this@MainPage,AnalysisActivity::class.java)
//                startActivity(intent)
//            }
//            releaseLayout.addView(releaseItemView)
//        }


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

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu,menu)
    }

    override fun onStart() {
        super.onStart()
        registerForContextMenu(myselfImageView)
    }

    override fun onStop() {
        super.onStop()
        registerForContextMenu(myselfImageView)
    }

}

