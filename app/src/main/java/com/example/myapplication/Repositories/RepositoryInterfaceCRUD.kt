package com.example.myapplication.Repositories

import com.example.myapplication.Domain.Entity

interface RepositoryInterfaceCRUD<ID,E: Entity<ID>> :RepositoryInterface<ID,E> {
    fun findOne(id:ID): E
    override fun findAll():Iterable<E>
    fun save(entity:E): Boolean
    fun delete(id:ID):Boolean
    fun update(entity:E): Boolean
}