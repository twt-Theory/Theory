package com.example.twttheory.manage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.twttheory.R
import com.example.twttheory.mainPage.SettingFragment
import com.example.twttheory.useful.ToastType
import com.example.twttheory.useful.makeToast

class ManageActivity(private val paper_id: Int) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        //选择专业限制
        val chooseLimiter: AlertDialog
        val changeBt = findViewById<Button>(R.id.button).apply {
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
    }
}
