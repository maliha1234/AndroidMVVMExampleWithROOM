package com.example.demoLogin.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDaoNew {
    @Query("SELECT * FROM PERSON ORDER BY ID")
    fun loadAllPersons(): Flow<List<PersonNew>>

    @Insert
    suspend fun insertPerson(personN: PersonNew?)

    @Update
    suspend fun updatePerson(person: PersonNew?)

    @Delete
    suspend fun delete(person: PersonNew?)

    @Query("SELECT * FROM PERSON WHERE id = :id")
    fun loadPersonById(id: Int): PersonNew?
}
