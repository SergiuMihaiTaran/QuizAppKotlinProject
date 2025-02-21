package com.example.myapplication.Service

import android.util.Log
import com.example.myapplication.Domain.Question
import com.example.myapplication.Repositories.QuestionDBRepository
import com.example.myapplication.Repositories.RepositoryInterface
import kotlin.random.Random

class QuestionsService(repo: QuestionDBRepository) : GeneralService<Int, Question>(
    repo
) {
    fun getNQuestions(n: Int): MutableList<Question> {
        val allQuestions = repo.findAll().toMutableList()
        if (allQuestions.isEmpty()) return mutableListOf() // Handle empty list case
        Log.d("QuestionsService", "Total questions: ${allQuestions.size}")
        return allQuestions.shuffled().take(n.coerceAtMost(allQuestions.size)).toMutableList()
    }
}