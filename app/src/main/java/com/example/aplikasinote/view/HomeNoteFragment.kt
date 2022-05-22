package com.example.aplikasinote.view

import android.app.ProgressDialog.show
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasinote.R
import com.example.aplikasinote.adapter.AdapterNote
import com.example.aplikasinote.datastore.UserManager
import com.example.aplikasinote.roomdatabase.NoteDatabase
import kotlinx.android.synthetic.main.fragment_home_note.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class HomeNoteFragment : Fragment() {

    lateinit var userdatastor: UserManager

    //    VARIABEL ROOM DATABASE
    private var notedb : NoteDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userdatastor = UserManager(requireContext())

        notedb = NoteDatabase.getInstance(requireContext())

        //        Baca & ambil data nama dari datastore
        userdatastor.userEmail.asLiveData().observe(requireActivity(),{
            tv_welcome_username.text = it.toString()
        })

        fab_add_note.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeNoteFragment_to_addNewNoteFragment)
        }
        CheckNote()
        GetNote()
    }

    fun GetNote(){
        rv_list_note.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        GlobalScope.async {
            val datanote = notedb?.NoteDao()?.getallNote()
            activity?.runOnUiThread {
                datanote.let {
                    val adapter = AdapterNote(it!!){
                        val pindah = Navigation.findNavController(view).navigate(R.id.action_homeNoteFragment_to_detailNoteFragment)
                        pindah.putExtra("DETAIL_FILM", it)
                        startActivity(pindah)
                    }
                    rv_list_note.adapter = adapter
                }
            }
        }
    }

    fun CheckNote(){
        GlobalScope.async {
            val checkdb = notedb?.NoteDao()?.getallNote()
            if (checkdb.isNullOrEmpty()) {
                tv_catatan_kosong.setText("Belum Ada Catatan")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        GetNote()
    }

    override fun onDestroy() {
        super.onDestroy()
        NoteDatabase.destroyInstance()
    }

}