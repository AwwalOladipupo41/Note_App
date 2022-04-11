package bgtap.babbangona.noteapp.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import bgtap.babbangona.noteapp.dao.NoteDao
import bgtap.babbangona.noteapp.entities.Notes


@Database
    (entities = [Notes::class], version=1, exportSchema=false)
abstract class Notes_Database:RoomDatabase() {

    companion object{
        var notesDatabase:Notes_Database?=null

        @Synchronized
        fun getDatabase(context: Context):Notes_Database{
            if(notesDatabase!=null){
                notesDatabase= Room.databaseBuilder(
                    context,Notes_Database::class.java, "notes.db").build()

            }
            return notesDatabase!!
        }

    }
    abstract fun noteDao():NoteDao
}