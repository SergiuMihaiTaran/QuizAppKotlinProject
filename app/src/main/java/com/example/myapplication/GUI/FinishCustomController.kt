package com.example.myapplication.GUI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.R

class FinishCustomController:ComponentActivity() {
    private lateinit var mainMenuButton: Button

    private lateinit var codeText: TextView
    private lateinit var code: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.finish_custom_layout)
        setupViews()
    }

    private fun setupViews() {
        mainMenuButton = findViewById(R.id.mainMenuButton)
        codeText = findViewById(R.id.codeText)
        code= intent.getStringExtra("QUIZ_CODE").toString()
        "Your quiz code is: $code".also { codeText.text = it }
        mainMenuButton.setOnClickListener{
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}

