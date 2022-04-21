package bgtap.babbangona.noteapp

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import bgtap.babbangona.noteapp.adapter.NotesAdapter
import bgtap.babbangona.noteapp.database.NotesDatabase
import bgtap.babbangona.noteapp.databinding.ActivityMainBinding.inflate
import bgtap.babbangona.noteapp.databinding.FragmentCreateNoteBinding.inflate
import bgtap.babbangona.noteapp.entities.Notes
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import java.util.*
import java.util.zip.Inflater
import kotlin.collections.ArrayList

class HomeFragment : BaseFragment() {

    var arrayNotes = ArrayList<Notes>()
    var notesAdapter: NotesAdapter = NotesAdapter()
    override val colorView: Any
        get() = TODO("Not yet implemented")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.setHasFixedSize(false)

        recycler_view.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        launch {
            context?.let {
                var notes = NotesDatabase.getDatabase(it).noteDao().getAllNotes()
                notesAdapter!!.setData(notes)
                arrayNotes = notes as ArrayList<Notes>
                recycler_view.adapter = notesAdapter
            }
        }

        notesAdapter!!.setOnClickListener(onClicked)

        fabBtnCreateNote.setOnClickListener {
            replaceFragment(CreateNoteFragment.newInstance(), false)
        }

       // class HomeFragment : Fragment() {
       //     override fun onCreate(savedInstanceState: Bundle?) {
        //        super.onCreate(savedInstanceState)
        //        setHasOptionsMenu(true)
        //    }
       // }

      //   fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

         //   val searchItem = menu.findItem(R.id.menu_search)
         //   val search_view = searchItem.actionView as SearchView
         //   search_view.isSubmitButtonEnabled = true
          //  super.onCreateOptionsMenu(menu, inflater)

          //   toolbar.setOnMenuItemClickListener {
           //      onOptionsItemSelected(it)
          //   }
            search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {

                    var tempArr = ArrayList<Notes>()

                    for (arr in arrayNotes) {
                        if (arr.title!!.toLowerCase(Locale.getDefault()).contains(p0.toString())) {
                            tempArr.add(arr)
                        }
                    }

                    notesAdapter.setData(tempArr)
                    notesAdapter.notifyDataSetChanged()
                    return true
                }

            })


    }



    private val onClicked = object :NotesAdapter.OnItemClickListener{
        override fun onClicked(notesId: Int) {


            var fragment :Fragment
            var bundle = Bundle()
            bundle.putInt("noteId",notesId)
            fragment = CreateNoteFragment.newInstance()
            fragment.arguments = bundle

            replaceFragment(fragment,false)
        }

    }


    fun replaceFragment(fragment:Fragment, istransition:Boolean){
        val fragmentTransition = activity!!.supportFragmentManager.beginTransaction()

        if (istransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransition.replace(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }




}