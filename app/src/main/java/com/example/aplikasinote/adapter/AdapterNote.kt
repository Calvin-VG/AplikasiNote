package com.example.aplikasinote.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasinote.R
import com.example.aplikasinote.roomdatabase.Note
import com.example.aplikasinote.roomdatabase.NoteDatabase
import com.example.aplikasinote.view.MainActivity
import kotlinx.android.synthetic.main.fragment_delete_note.view.*
import kotlinx.android.synthetic.main.fragment_home_note.view.*
import kotlinx.android.synthetic.main.fragment_update_note.view.*
import kotlinx.android.synthetic.main.item_adapter_note.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AdapterNote(val listDataNote : List<Note>, private var onClick: (Note) -> Unit): RecyclerView.Adapter<AdapterNote.ViewHolder>() {

    private var notedatabase : NoteDatabase? = null

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNote.ViewHolder {
        val viewitem = LayoutInflater.from(parent.context).inflate(R.layout.item_adapter_note,parent, false)
        return ViewHolder(viewitem)
    }

    override fun onBindViewHolder(holder: AdapterNote.ViewHolder, position: Int) {
        holder.itemView.tv_judul_note.text = listDataNote[position].judul
        holder.itemView.tv_catatan_note.text = listDataNote[position].catatan

        holder.itemView.rv_list_note.setOnClickListener {
            onClick(listDataNote[position])
        }

//        HAPUS DATA
        holder.itemView.btn_delete_note.setOnClickListener {

            notedatabase = NoteDatabase.getInstance(it.context)

            val interfacedelete = LayoutInflater.from(it.context)
                .inflate(R.layout.fragment_delete_note, null, false)
            val dialogdelete = AlertDialog.Builder(it.context)
                .setView(interfacedelete)
                .create()

            interfacedelete.btn_cancel_hapus_data.setOnClickListener {
                dialogdelete.dismiss()
            }
            interfacedelete.btn_hapus_hapus_data.setOnClickListener {
                GlobalScope.async {
                    val result = notedatabase?.NoteDao()?.deleteNote(listDataNote[position])
                    (interfacedelete.context as MainActivity).runOnUiThread {
                        if(result != 0){
                            Toast.makeText(it.context, "Data ${listDataNote[position].judul} Berhasil Dihapus", Toast.LENGTH_LONG).show()
                            (interfacedelete.context as MainActivity).recreate()
                        }else{
                            Toast.makeText(it.context, "Data ${listDataNote[position].judul} Gagal Dihapus", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            dialogdelete.show()
        }

//        UPDATE DATA
        holder.itemView.btn_update_note.setOnClickListener {
            notedatabase = NoteDatabase.getInstance(it.context)
            val interfaceupdate = LayoutInflater.from(it.context).inflate(R.layout.fragment_update_note, null, false)
            val dialogupdate = AlertDialog.Builder(it.context)
                .setView(interfaceupdate)
                .create()

            interfaceupdate.et_edit_judul_data.setText(listDataNote[position].judul)
            interfaceupdate.et_edit_catatan_data.setText(listDataNote[position].catatan)

            interfaceupdate.btn_edit_data.setOnClickListener {
                val judulbaru = interfaceupdate.et_edit_judul_data.text.toString()
                val catatanbaru = interfaceupdate.et_edit_catatan_data.text.toString()

                listDataNote[position].judul = judulbaru
                listDataNote[position].catatan = catatanbaru

                GlobalScope.async {
                    val update = notedatabase?.NoteDao()?.updateNote(listDataNote[position])

                    (interfaceupdate.context as MainActivity).runOnUiThread{
                        if(update != 0){
                            Toast.makeText(it.context, "Data $judulbaru Berhasil Diupdate", Toast.LENGTH_SHORT).show()
                            //recreate activity
                            (interfaceupdate.context as MainActivity).recreate()
                        }else{
                            Toast.makeText(it.context, "Data $judulbaru Gagal Diupdate", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            dialogupdate.show()
        }
    }

    override fun getItemCount(): Int {
        return listDataNote.size
    }
}