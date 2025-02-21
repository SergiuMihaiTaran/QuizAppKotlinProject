package com.example.myapplication.Domain

import java.io.Serializable

open class Entity<ID> :Serializable{
    var id:ID?=null;
    companion object{
        private const val serialVersionUID=7331115341259248461L
    }
}