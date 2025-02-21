package com.example.myapplication.Domain

class Question  (
    var text:String?=null,
    var option1:String?=null,
    var option2:String?=null,
    var option3:String?=null,
    var option4:String?=null,
    var correctOption:Int?=0,
    var imageID:String?=null
):Entity<Int>(){

}
