package com.example.twttheory.mainPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.twttheory.R
import java.sql.Time

class CreatTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creat_task)

        val settingButton : Button = findViewById(R.id.settings)
        var settingFragment : Fragment? = null
        var isSettingVisible = false;
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val raturnButton = findViewById<ImageView>(R.id.return_button)
        val addedQList = findViewById<RecyclerView>(R.id.added_questions)
        val typeChoose = findViewById<ViewGroup>(R.id.choose_type)
        val b1 = findViewById<RadioButton>(R.id.single_selection)
        val b2 = findViewById<RadioButton>(R.id.multiple_selection)
        val b3 = findViewById<RadioButton>(R.id.text_question)
        val b4 = findViewById<RadioButton>(R.id.inventory_problem)
        val b5 = findViewById<RadioButton>(R.id.sequencing_question)
        raturnButton.setOnClickListener {
            this.finish()
        }
        settingButton.setOnClickListener {
            if (!isSettingVisible){
                if (settingFragment == null){
                    settingFragment = SettingFragment
                    mFragmentTransaction.add(R.id.setting,settingFragment!!)
                    mFragmentTransaction.commit()         //不知道为什么没有效果（不能点一次隐藏，再点一次显示）
                }else{
                    mFragmentTransaction.show(settingFragment!!)
                }
                isSettingVisible = true
            }else{
                mFragmentTransaction.hide(settingFragment!!)
                isSettingVisible = false
            }
        }




    }
}
