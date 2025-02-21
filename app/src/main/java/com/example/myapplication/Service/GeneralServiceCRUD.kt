package com.example.myapplication.Service

import com.example.myapplication.Domain.Entity
import com.example.myapplication.Repositories.RepositoryInterface
import com.example.myapplication.Repositories.RepositoryInterfaceCRUD

open class GeneralServiceCRUD<ID,E:Entity<ID>>(
    private val repository: RepositoryInterfaceCRUD<ID, E>
) : GeneralService<ID, E>(repository) {
    fun findOne(id:ID): E{
        return repository.findOne(id)
    }

    fun save(entity:E): Boolean{
        return repository.save(entity)
    }
    fun delete(id:ID):Boolean{
        return repository.delete(id)
    }
    fun update(entity:E): Boolean{
        return repository.update(entity)
    }
}