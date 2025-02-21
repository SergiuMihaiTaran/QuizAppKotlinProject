package com.example.myapplication.GUI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.DataBaseHelperQuiz
import com.example.myapplication.R

class MainActivity : ComponentActivity() {
    lateinit var startButton: Button
    lateinit var statsButton:Button
    lateinit var createButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.start_page)
        val dbHelper = DataBaseHelperQuiz(this, null)
        dbHelper.addAllQuestions()
        startButton=findViewById(R.id.play_button)
        startButton.setOnClickListener{
            Intent(this, QuizMainUI::class.java).also {
            startActivity(it)
            }
        }
        statsButton=findViewById(R.id.stats_button)
        statsButton.setOnClickListener{
            Intent(this,StatsController::class.java).also {
                startActivity(it)
            }
        }
        createButton=findViewById(R.id.createButton)
        createButton.setOnClickListener{
            Intent(this,CustomSettingsController::class.java).also {
                startActivity(it)
            }
        }
    }
}
