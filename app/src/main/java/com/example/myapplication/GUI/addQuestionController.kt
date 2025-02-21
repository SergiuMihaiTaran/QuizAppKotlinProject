package com.example.myapplication.GUI

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.R

class addQuestionController:ComponentActivity() {
    private lateinit var questionNumberText:TextView
    private var questionNumber:Int=1
    private var totalQuestions:Int=0
    private var quizTitle:String=""
    private var quizDescription:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.add_question_layout)
        setupViews()
    }
    private fun setupViews(){
        questionNumberText=findViewById(R.id.questionNumberText)
        totalQuestions=intent.getIntExtra("QUESTIONS_NUMBER",0)
        quizTitle= intent.getStringExtra("TITLE").toString()
        quizDescription=intent.getStringExtra("DESCRIPTION").toString()
        ("$questionNumber/$totalQuestions").also { questionNumberText.text = it }
    }
}