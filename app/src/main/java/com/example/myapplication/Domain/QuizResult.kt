package com.example.myapplication.Domain

import java.time.LocalDateTime

class QuizResult(
    var dateTime: String="",
    var numberOfQuestions: Int=0,
    var numberOfCorrectAnswers: Int=0,
    var isCustom: Boolean?=false
):Entity<Int>() {
}