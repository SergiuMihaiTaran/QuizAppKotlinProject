package com.example.myapplication.Repositories

import com.example.myapplication.Domain.Entity

interface RepositoryInterface<ID,E:Entity<ID>> {
    fun findAll(): Iterable<E>

}