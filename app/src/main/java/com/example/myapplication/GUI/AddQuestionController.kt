package com.example.myapplication.GUI

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.Domain.Question
import com.example.myapplication.R

class AddQuestionController:ComponentActivity() {
    private lateinit var questionNumberText:TextView
    private var questionNumber:Int=1
    private var totalQuestions:Int=0
    private var quizTitle:String=""
    private var quizDescription:String=""
    private var questionList= mutableListOf<Question>()
    private lateinit var questionText:EditText
    private lateinit var questionOption1:EditText
    private lateinit var questionOption2:EditText
    private lateinit var questionOption3:EditText
    private lateinit var questionOption4:EditText
    private lateinit var radioGroup:RadioGroup
    private lateinit var nextQButton: Button
    private lateinit var errorText:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.add_question_layout)
        setupViews()
        nextQButton.setOnClickListener{
            maybeGoToNextQuestion()
        }

    }
    private fun maybeGoToNextQuestion(){
        if(!canNextQuestion()) {
            errorText.visibility=View.VISIBLE
            return
        }
        goToNextQuestion()
    }
    private fun goToNextQuestion(){
        questionList.add(Question(
            questionText.text.toString(), questionOption1.text.toString(),
            questionOption2.text.toString(),
            questionOption3.text.toString(),
            questionOption4.text.toString(),getSelectedAnswerIndex(),""))
        resetViews()
        questionNumber++
        ("$questionNumber/$totalQuestions").also { questionNumberText.text = it }
    }
    private fun canNextQuestion():Boolean{
        if(questionOption1.text.isEmpty() || questionOption2.text.isEmpty() || questionOption3.text.isEmpty()
            || questionOption4.text.isEmpty() || questionText.text.isEmpty() )
            return false
        if(radioGroup.checkedRadioButtonId==-1)
            return false
        return true
    }
    private fun resetViews(){
        radioGroup.clearCheck()
        questionText.text.clear()
        questionOption1.text.clear()
        questionOption2.text.clear()
        questionOption3.text.clear()
        questionOption4.text.clear()
        errorText.visibility=View.GONE
    }
    private fun getSelectedAnswerIndex(): Int {
        return when (radioGroup.checkedRadioButtonId) {
            R.id.radioButton1 -> 1
            R.id.radioButton2 -> 2
            R.id.radioButton3 -> 3
            R.id.radioButton4 -> 4
            else -> -1 // No selection
        }
    }
    private fun setupViews(){
        questionNumberText=findViewById(R.id.questionNumberText)
        totalQuestions=intent.getIntExtra("QUESTIONS_NUMBER",0)
        quizTitle= intent.getStringExtra("TITLE").toString()
        quizDescription=intent.getStringExtra("DESCRIPTION").toString()
        ("$questionNumber/$totalQuestions").also { questionNumberText.text = it }
        radioGroup=findViewById(R.id.radioGroup)
        questionText=findViewById(R.id.questionTextEdit)
        questionOption1=findViewById(R.id.option1Text)
        questionOption2=findViewById(R.id.option2Text)
        questionOption3=findViewById(R.id.option3Text)
        questionOption4=findViewById(R.id.option4Text)
        nextQButton=findViewById(R.id.nextQuestionButton)
        errorText=findViewById(R.id.errorText)

    }
}