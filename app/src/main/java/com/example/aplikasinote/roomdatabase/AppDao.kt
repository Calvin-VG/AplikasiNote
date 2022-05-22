package com.example.aplikasinote.roomdatabase

import androidx.room.*

@Dao
interface AppDao {

    @Insert
    fun insertAkun(akun: Akun) : Long

    @Insert
    fun insertNote(note: Note) : Long

    @Query("SELECT * FROM Note")
    fun getallNote() : List<Note>

    @Delete
    fun deleteNote(note: Note) : Int

    @Update
    fun updateNote(note: Note) : Int

}