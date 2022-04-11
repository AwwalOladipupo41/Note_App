package bgtap.babbangona.noteapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName ="Notes")
data class Notes (

        @PrimaryKey(autoGenerate = true)
        var id:Int,

        @ColumnInfo(name="title")
        var title:String,

        @ColumnInfo(name="sub_title")
        var subTitle:String,

        @ColumnInfo(name="date_title")
        var dateTime:String,

        @ColumnInfo(name="note_text")
        var noteText:String,

        @ColumnInfo(name="image_path")
        var imagePath:String,

        @ColumnInfo(name="web_link")
        var webLink:String,

        @ColumnInfo(name="color")
        var color:String,

        ):Serializable{

        override fun toString(): String {
                return "$title : $dateTime"
        }
}