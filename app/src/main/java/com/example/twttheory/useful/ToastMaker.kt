package com.example.twttheory.useful

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.twttheory.R

enum class ToastType {
    Success, Error
}

fun AppCompatActivity.makeToast(context: Context, msg: String, type: ToastType) {
    Toast(context).apply {
        setGravity(Gravity.BOTTOM, 0, 300)
        duration = Toast.LENGTH_SHORT
        when (type) {
            ToastType.Success -> {
                view = layoutInflater.inflate(
                    R.layout.success_toast_layout, findViewById(R.id.success_toast)
                )
                view.findViewById<TextView>(R.id.success_toast_text).apply { text = msg }
            }
            ToastType.Error -> {
                view = layoutInflater.inflate(
                    R.layout.error_toast_layout, findViewById(R.id.error_toast)
                )
                view.findViewById<TextView>(R.id.error_toast_text).apply { text = msg }
            }
        }
    }.show()
}