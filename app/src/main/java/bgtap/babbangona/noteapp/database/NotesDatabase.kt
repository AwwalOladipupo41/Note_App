package bgtap.babbangona.noteapp.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import bgtap.babbangona.noteapp.entities.Notes
import bgtap.babbangona.noteapp.dao.NoteDao


@Database
    (entities = [Notes::class], version=1, exportSchema=false)
abstract class NotesDatabase:RoomDatabase() {

    companion object{
        var notesDatabase:NotesDatabase?=null

        @Synchronized
        fun getDatabase(context: Context):NotesDatabase{
            if(notesDatabase!=null){
                notesDatabase= Room.databaseBuilder(
                    context
                    ,NotesDatabase::class.java, "notes.db").allowMainThreadQueries().fallbackToDestructiveMigration().build()

            }
            return notesDatabase!!
        }

    }
    abstract fun noteDao(): NoteDao
}