package com.example.twttheory.manage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.twttheory.R

class ManageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        val titleInput = findViewById<EditText>(R.id.title1)
        val insInput = findViewById<EditText>(R.id.instruction_input)
        //选择专业限制
        val chooseLimiter : AlertDialog
        val isMajorLimited : CheckBox = findViewById(R.id.major_limit)

    }
}
