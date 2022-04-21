package bgtap.babbangona.noteapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import bgtap.babbangona.noteapp.database.NotesDatabase
import bgtap.babbangona.noteapp.entities.Notes
import bgtap.babbangona.noteapp.util.NoteBottomSheetFragment
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

//#171C26
    class CreateNoteFragment : BaseFragment(){
    var selectedColor = "#ffffff"
    var currentDate:String? = null
    private var noteId = -1
        override val colorView: Any
            get() = TODO("Not yet implemented")

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            noteId = requireArguments().getInt("noteId",-1)

        }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (noteId != -1){

            launch {
                context?.let {
                    var notes = NotesDatabase.getDatabase(it).noteDao().getSpecificNote(noteId)

                    etNoteTitle.setText(notes.title)
                    etNoteDesc.setText(notes.noteText)
                }
            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            BroadcastReceiver, IntentFilter("bottom_sheet_action")
        )


        val sdf = SimpleDateFormat("LLL dd, yyyy")

        currentDate = sdf.format(Date())



        tvNoteDate.text = currentDate

        imgDone.setOnClickListener {
            if (noteId != -1){
                updateNote()
            }else{
                saveNote()
            }
        }

        imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        imgMore.setOnClickListener{


            var noteBottomSheetFragment = NoteBottomSheetFragment.newInstance(noteId)
            noteBottomSheetFragment.show(requireActivity().supportFragmentManager,"Note Bottom Sheet Fragment")
        }

    }


    private fun updateNote(){
        launch {

            context?.let {
                var notes = NotesDatabase.getDatabase(it).noteDao().getSpecificNote(noteId)

                notes.title = etNoteTitle.text.toString()
                notes.noteText = etNoteDesc.text.toString()
                notes.NoteDate = currentDate
                notes.color = selectedColor


                NotesDatabase.getDatabase(it).noteDao().updateNote(notes)
                etNoteTitle.setText("")
                etNoteDesc.setText("")
                layoutImage.visibility = View.GONE
                imgNote.visibility = View.GONE
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }
        private fun saveNote(){

            if (etNoteTitle.text.isNullOrEmpty()){
                Toast.makeText(context,"Note Title is Required",Toast.LENGTH_SHORT).show()
            }


            else if (etNoteDesc.text.isNullOrEmpty()){

                Toast.makeText(context,"Note Description is Required",Toast.LENGTH_SHORT).show()
            }

            else{

                launch {
                    var notes = Notes()
                    notes.title = etNoteTitle.text.toString()
                    notes.noteText = etNoteDesc.text.toString()
                    notes.NoteDate = currentDate
                    notes.color = selectedColor

                    context?.let {
                        NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                        etNoteTitle.setText("")
                        etNoteDesc.setText("")
                        layoutImage.visibility = View.GONE
                        imgNote.visibility = View.GONE
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                }
            }

        }

      private fun deleteNote(){

           launch {
                context?.let {
                  NotesDatabase.getDatabase(it).noteDao().deleteSpecificNote(noteId)
                    requireActivity().supportFragmentManager.popBackStack()
                }
           }
       }


        private val BroadcastReceiver : BroadcastReceiver = object :BroadcastReceiver(){
            override fun onReceive(p0: Context?, p1: Intent?) {

                var actionColor = p1!!.getStringExtra("action")

                when(actionColor!!){

                    "Blue" -> {
                        selectedColor = p1.getStringExtra("selectedColor")!!


                    }

                    "Yellow" -> {
                        selectedColor = p1.getStringExtra("selectedColor")!!


                    }


                    "Pink" -> {
                        selectedColor = p1.getStringExtra("selectedColor")!!


                    }


                    "Green" -> {
                        selectedColor = p1.getStringExtra("selectedColor")!!


                    }


                    "Orange" -> {
                        selectedColor = p1.getStringExtra("selectedColor")!!


                    }


                    "Purple" -> {
                        selectedColor = p1.getStringExtra("selectedColor")!!


                    }


                    "DeleteNote" -> {
                        //delete note
                        deleteNote()
                    }


                    else -> {
                        layoutImage.visibility = View.GONE
                        imgNote.visibility = View.GONE
                        selectedColor = p1.getStringExtra("selectedColor")!!


                    }
                }
            }

        }



    }







