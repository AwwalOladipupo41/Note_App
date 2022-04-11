package bgtap.babbangona.noteapp.dao

import androidx.room.*
import bgtap.babbangona.noteapp.entities.Notes


@Dao
interface NoteDao {
    @get:Query("SELECT * FROM notes ORDER BY id DESC")
    val allNotes: List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(note:Notes)

    @Delete
    fun deleteNote(note:Notes)
}