package com.example.myapplication.Service

import com.example.myapplication.Domain.Entity
import com.example.myapplication.Repositories.RepositoryInterface
import kotlin.random.Random

open class GeneralService<ID ,E:Entity<ID>> (
    val repo:RepositoryInterface<ID,E>
)
{
    fun findAll():Iterable<E>{
        return repo.findAll()
    }


}