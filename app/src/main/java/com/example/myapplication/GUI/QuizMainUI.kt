package com.example.myapplication.GUI

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.Domain.Question
import com.example.myapplication.Domain.QuizResult
import com.example.myapplication.R
import com.example.myapplication.Repositories.QuestionDBRepository
import com.example.myapplication.Repositories.QuizResultsDBRepository
import com.example.myapplication.Service.QuestionsService
import com.example.myapplication.Service.QuizResultsService

class QuizMainUI : ComponentActivity() {
    private var numberOfCorrectAnswers=0
    private val numberOfTotalQuestions=5
    private var currentQuestionNumber=0
    private lateinit var randomQuestionList:List<Question>
    private lateinit var text2:TextView
    private lateinit var image:ImageView
    private lateinit var buttonOption1: Button
    private lateinit var buttonOption2: Button
    private lateinit var buttonOption3: Button
    private lateinit var buttonOption4: Button
    private lateinit var nextQuestionButton:Button
    private lateinit var questionNumber:TextView
    private lateinit var finishQuizButton:Button
    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initResources()
        val repo=QuestionDBRepository(this)
        val service=QuestionsService(repo)

        randomQuestionList=service.getNQuestions(10)
        initializeQuestion(randomQuestionList[0])
    }
    @SuppressLint("DiscouragedApi")
    fun initializeQuestion(currentQuestion:Question){
        questionNumber.text="Question "+(currentQuestionNumber+1)+"/"+numberOfTotalQuestions
        val imageName = currentQuestion.imageID
        val resourceId = resources.getIdentifier(imageName, "drawable", packageName)
        if (resourceId != 0) {
            image.setImageResource(resourceId)
        } else {
            image.setImageResource(0)
            image.setBackgroundColor(Color.parseColor("#1E201E"))
        }
        val rightAnswer=currentQuestion.correctOption
        text2.text = currentQuestion.text ?: "No question available"
        buttonOption1.text = currentQuestion.option1
        buttonOption2.text = currentQuestion.option2
        buttonOption3.text = currentQuestion.option3
        buttonOption4.text = currentQuestion.option4
        if (rightAnswer != null) {
            initButtons(rightAnswer)
        }
    }
    private fun initButtons(rightAnswer:Int){
        buttonOption1.setOnClickListener{
                whenOptionButtonPressedProcessor(1, rightAnswer)

        }
        buttonOption2.setOnClickListener{
                whenOptionButtonPressedProcessor(2, rightAnswer)
        }
        buttonOption3.setOnClickListener{
                whenOptionButtonPressedProcessor(3, rightAnswer)
        }
        buttonOption4.setOnClickListener{
                whenOptionButtonPressedProcessor(4, rightAnswer)
        }
        nextQuestionButton.setOnClickListener{
            nextQuestionProcessor()
        }
    }
    private fun nextQuestionProcessor(){

        resetButtonStates()
        nextQuestionButton.visibility=View.INVISIBLE
        val currentQuestion=randomQuestionList[currentQuestionNumber]
        initializeQuestion(currentQuestion)
    }
    private fun finishQuizProcessor(){
        finishQuizButton.visibility=View.VISIBLE
        finishQuizButton.text= getString(R.string.finish_quiz)
        Log.d(null,"sasad")
        finishQuizButton.setOnClickListener {
            val intent = Intent(this, FinishQuizController::class.java).apply {
                putExtra("QUESTION_NUMBER", numberOfTotalQuestions) // Total questions
                putExtra("CORRECT_QUESTION_NUMBER", numberOfCorrectAnswers) // Correct answers
            }
            startActivity(intent)
        }
        addResult()
    }
    private fun addResult(){
        val resultsRepo=QuizResultsDBRepository(this)
        val resultsService=QuizResultsService(resultsRepo)
        resultsService.save(QuizResult("",numberOfTotalQuestions,numberOfCorrectAnswers,false))
    }
    private fun resetButtonStates() {
        buttonOption1.setBackgroundResource(R.drawable.rounded_button)
        buttonOption2.setBackgroundResource(R.drawable.rounded_button)
        buttonOption3.setBackgroundResource(R.drawable.rounded_button)
        buttonOption4.setBackgroundResource(R.drawable.rounded_button)
        buttonOption1.isEnabled=true
        buttonOption2.isEnabled=true
        buttonOption3.isEnabled=true
        buttonOption4.isEnabled=true
    }
    private fun whenOptionButtonPressedProcessor(buttonPressed:Int, correctOption:Int){
        val buttonList= mutableListOf(buttonOption1,buttonOption2,buttonOption3,buttonOption4)
        if(buttonPressed==correctOption) {
            buttonList[buttonPressed - 1].setBackgroundResource(R.drawable.rounded_button_right_answer)
            numberOfCorrectAnswers++
        }
            else{
            buttonList[buttonPressed-1].setBackgroundResource(R.drawable.round_button_wrong_answer)
            buttonList[correctOption-1].setBackgroundResource(R.drawable.rounded_button_right_answer)

        }
        currentQuestionNumber++
        buttonList.forEach{ b-> b.isEnabled=false}
        if(currentQuestionNumber==numberOfTotalQuestions) {
            finishQuizProcessor()
            return
        }
        nextQuestionButton.visibility= View.VISIBLE
    }
    @SuppressLint("MissingInflatedId")
    private fun initResources(){
        setContentView(R.layout.quiz_main_layout)
        text2=findViewById(R.id.questionText)
        image=findViewById(R.id.QuestionImage)
        buttonOption1=findViewById(R.id.option1Button)
        buttonOption2=findViewById(R.id.option2Button)
        buttonOption3=findViewById(R.id.option3Button)
        buttonOption4=findViewById(R.id.option4Button)
        nextQuestionButton=findViewById(R.id.nextQuestionButton)
        questionNumber=findViewById(R.id.questionNumber)
        finishQuizButton=findViewById(R.id.finishQuizButton)
    }
}