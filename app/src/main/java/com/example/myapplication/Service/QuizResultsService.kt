package com.example.myapplication.Service

import com.example.myapplication.Domain.QuizResult
import com.example.myapplication.Repositories.QuizResultsDBRepository

class QuizResultsService(repo:QuizResultsDBRepository):GeneralServiceCRUD<Int,QuizResult>(repo) {
    fun getPassedQuizes():MutableList<QuizResult>{
        val passedList= mutableListOf<QuizResult>()
        findAll().forEach{qr->if(qr.numberOfCorrectAnswers.toDouble()/qr.numberOfQuestions>=0.5) passedList.add(qr)}
        return passedList
    }
    fun getFailedQuizes() :MutableList<QuizResult>{
        val failedList= mutableListOf<QuizResult>()
        findAll().forEach{qr->if(qr.numberOfCorrectAnswers.toDouble()/qr.numberOfQuestions<0.5) failedList.add(qr)}
        return  failedList
    }
}