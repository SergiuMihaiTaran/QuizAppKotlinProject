package com.example.myapplication.GUI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.Domain.Question
import com.example.myapplication.R
import com.example.myapplication.Utility
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class AddQuestionController:ComponentActivity() {
    private lateinit var questionNumberText:TextView
    private var questionNumber:Int=1
    private var totalQuestions:Int=0
    private var quizTitle:String=""
    private val quizCode=Utility.generateQuizCode()
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
    private val cloudDB=FirebaseFirestore.getInstance()
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

        questionList.add(
            Question(
                questionText.text.toString(),
                questionOption1.text.toString(),
                questionOption2.text.toString(),
                questionOption3.text.toString(),
                questionOption4.text.toString(),
                getSelectedAnswerIndex(),
                ""
            )
        )

        questionNumber++
        if (questionNumber > totalQuestions) {
            saveQuizToFirestore()
            goToFinishScreen()
            return
        }

        resetViews()
        ("$questionNumber/$totalQuestions").also { questionNumberText.text = it }
    }
    private fun goToFinishScreen() {

            val intent = Intent(this, FinishCustomController::class.java).apply {
                putExtra("QUIZ_CODE", quizCode) // Total questions
            }
            startActivity(intent)

    }
    private fun saveQuizToFirestore() {

        val quizData = hashMapOf(
            "code" to quizCode,
            "questions" to questionList.map { question ->
                hashMapOf(
                    "questionText" to question.text,
                    "option1" to question.option1,
                    "option2" to question.option2,
                    "option3" to question.option3,
                    "option4" to question.option4,
                    "correctAnswerIndex" to question.correctOption
                )
            },
            "title" to quizTitle,
            "description" to quizDescription,
            "timestamp" to FieldValue.serverTimestamp() // Add timestamp for ordering
        )

        cloudDB.collection("quiz")
            .document(quizCode)
            .set(quizData)

            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Quiz saved with ID: ${quizCode}")
                questionList.clear()
                questionNumber = 1
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error saving quiz", e)
            }
    }
//when the button is first set

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
        if(questionNumber==totalQuestions) {
            nextQButton.text = getString(R.string.finish_quiz)

        }
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
        if(questionNumber==totalQuestions) {
            nextQButton.text = getString(R.string.finish_quiz)

        }
    }
}