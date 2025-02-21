package com.example.myapplication.Repositories

import android.content.Context
import com.example.myapplication.DataBaseHelperQuiz
import com.example.myapplication.Domain.Question

class QuestionDBRepository(private val context: Context) :RepositoryInterface<Int,Question>{
    private val dataBase=DataBaseHelperQuiz(context,null)
    override fun findAll(): Iterable<Question> {
        val questions= mutableListOf<Question>()
        val cursor=dataBase.getQuestion()
        cursor?.use{
            while(cursor.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperQuiz.ID_COL))
                val questionMessage = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperQuiz.QUESTION_MESSAGE))
                val option1 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperQuiz.OPTION1))
                val option2 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperQuiz.OPTION2))
                val option3 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperQuiz.OPTION3))
                val option4 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperQuiz.OPTION4))
                val correctOption = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperQuiz.CORRECT_OPTION_INDEX))
                val imageId = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperQuiz.IMAGE_ID))
                val question=Question(questionMessage,option1,option2,option3,option4,correctOption,imageId)
                question.id=id
                questions.add(question)

            }

        }

        return questions
    }
}