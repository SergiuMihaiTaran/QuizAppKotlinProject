package com.example.myapplication.Repositories

import android.content.Context
import com.example.myapplication.DataBaseHelperQuiz
import com.example.myapplication.Domain.QuizResult

class QuizResultsDBRepository (private val context: Context) :RepositoryInterfaceCRUD<Int,QuizResult> {
    private val dataBase= DataBaseHelperQuiz(context,null)
    //don't use,not implemented
    override fun findOne(id: Int): QuizResult {
        return QuizResult()
    }

    override fun findAll(): Iterable<QuizResult> {
        val results= mutableListOf<QuizResult>()
        val cursor=dataBase.getAllQuizResults()
        cursor?.use{
            while(cursor.moveToNext()){
                val id=cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperQuiz.RESULT_ID))
                val dateTimeStr = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperQuiz.TIMESTAMP))
                val numberOfQuestions = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperQuiz.NUMBER_OF_QUESTIONS))
                val correctAnswers = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperQuiz.CORRECT_ANSWERS))
                val isCustom = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperQuiz.IS_CUSTOM)) == 1
                val qr=QuizResult(dateTimeStr,numberOfQuestions,correctAnswers,isCustom)
                qr.id=id
                results.add(qr)
            }

        }
        cursor?.close()
        return results
    }
    //don't use,not implemented
    override fun update(entity: QuizResult): Boolean {
        return false
    }
    //don't use,not implemented
    override fun delete(id: Int): Boolean {
        return false
    }

    override fun save(entity: QuizResult): Boolean {
        return dataBase.addQuizResult(entity.numberOfQuestions,entity.numberOfCorrectAnswers,entity.isCustom)
    }
}