package com.example.myapplication.GUI

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.R


class FinishQuizController:ComponentActivity() {
    private var questionNumber = 0
    private var correctQuestionNumber = 0
    private lateinit var mainMenuButton:Button
    private lateinit var finishPrompt:TextView
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.finish_quiz_layout)
        setup()
        if(correctQuestionNumber.toDouble()/questionNumber.toDouble()>=0.5)
            finishPrompt.setTextColor(Color.GREEN)
        else{
            finishPrompt.setTextColor(Color.RED)
        }
        finishPrompt.text = "You answered $correctQuestionNumber/$questionNumber questions correctly!"
        mainMenuButton.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
   private fun setup(){

       questionNumber = intent.getIntExtra("QUESTION_NUMBER", 0) // Default value = 0
       correctQuestionNumber = intent.getIntExtra("CORRECT_QUESTION_NUMBER", 0)

       // Initialize UI elements
       finishPrompt = findViewById(R.id.finishPrompt)
       mainMenuButton = findViewById(R.id.mainMenuButton)
   }
}