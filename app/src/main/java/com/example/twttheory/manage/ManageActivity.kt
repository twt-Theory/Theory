package com.example.twttheory.manage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.twttheory.R
import com.example.twttheory.exam.pauseExam
import com.example.twttheory.exam.deleteExam
import com.example.twttheory.mainPage.SettingFragment
import com.example.twttheory.service.RefreshState
import com.example.twttheory.useful.*

class ManageActivity(private val paper_id: Int) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)
        //选择专业限制
        val chooseLimiter: AlertDialog
        val confirmBt = findViewById<Button>(R.id.confirm_button).apply {
            setOnClickListener {
                val case = SettingFragment.changeSettings(paper_id)
                val msg: String = when (case) {
                    0 -> "问卷修改成功！"
                    1 -> "请设置问卷名称！"
                    2 -> "请设置问卷描述！"
                    3 -> "请设置答题时长！"
                    4 -> "请设置答题次数！"
                    5 -> "请设置问卷密码！"
                    else -> "问卷修改失败！"
                }
                if (case != 0) makeToast(this@ManageActivity, msg, ToastType.Error)
                else makeToast(this@ManageActivity, msg, ToastType.Success)
            }
        }
        val changeQuesBt = findViewById<Button>(R.id.changeQuesBt).apply {
            setOnClickListener {
                //跳转至修改题目页面
            }
        }
        val pauseBt = findViewById<Button>(R.id.pauseBt).apply {
            setOnClickListener {
                //暂停功能尚未实现！！！！！！！！！！！！
//                pauseExam(paper_id) {
//                    when (it) {
//                        is RefreshState.Success -> makeToast(
//                            this@ManageActivity,
//                            "暂停问卷成功！",
//                            ToastType.Success
//                        )
//                        else -> makeToast(this@ManageActivity, "暂停问卷失败！", ToastType.Error)
//                    }
//                }
            }
        }
        val deleteBt = findViewById<Button>(R.id.deleteBt).apply {
            setOnClickListener {
                deleteExam(paper_id) {
                    when (it) {
                        is RefreshState.Success -> {
                            makeToast(this@ManageActivity, "删除问卷成功！", ToastType.Success)
                            finish()
                        }
                        else -> makeToast(this@ManageActivity, "删除问卷失败！", ToastType.Error)
                    }
                }
            }
        }
    }
}
