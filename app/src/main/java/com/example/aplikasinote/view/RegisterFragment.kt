package com.example.aplikasinote.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.aplikasinote.R
import com.example.aplikasinote.datastore.UserManager
import com.example.aplikasinote.roomdatabase.Akun
import com.example.aplikasinote.roomdatabase.AkunDatabase
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    lateinit var userdatastorange: UserManager
    private var akundb: AkunDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userdatastorange = UserManager(requireContext())
        akundb = AkunDatabase.getInstance(requireContext())

        btn_daftar.setOnClickListener {
            val username = et_masukkan_username_register.text.toString()
            val email = et_masukkan_email_register.text.toString()
            val password = et_masukkan_password_register.text.toString()
            val konfirmasi_password = et_masukkan_konfirmasi_password_register.text.toString()

            GlobalScope.launch {
                userdatastorange.saveData(username, email, password)
            }

            GlobalScope.async {
                val username_db = et_masukkan_username_register.text.toString()
                val email_db = et_masukkan_email_register.text.toString()
                val password_db = et_masukkan_password_register.text.toString()
                val konfirmasi_password_db = et_masukkan_konfirmasi_password_register.text.toString()
                val addakun = akundb?.AkunDao()?.insertAkun(Akun(null, username_db, email_db, password_db, konfirmasi_password_db))

                activity?.runOnUiThread {
                    if (addakun != 0.toLong()) {
                        Toast.makeText(requireContext(), "Akun $username_db Berhasil Ditambahkan", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(requireContext(), "Akun $username_db Gagal Ditambahkan", Toast.LENGTH_LONG).show()
                    }
                }
            }

            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginNoteFragment)
        }
    }

}