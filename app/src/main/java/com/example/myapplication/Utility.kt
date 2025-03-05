package com.example.myapplication

 class Utility {

     companion object{
     public fun generateQuizCode(): String {
         val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
         return (1..6)
             .map { charset.random() }
             .joinToString("")
     }
}}