package com.example.twttheory.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toolbar
import com.example.twttheory.R

class ResultActivity : AppCompatActivity() {
    lateinit var toolBar : Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val returnButton = findViewById<ImageView>(R.id.return_button)
        returnButton.setOnClickListener {
            finish()
        }
    }



}

