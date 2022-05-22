package com.example.aplikasinote.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.example.aplikasinote.R
import com.example.aplikasinote.datastore.UserManager
import kotlinx.android.synthetic.main.fragment_login_note.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginNoteFragment : Fragment() {

    lateinit var userdatastore: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userdatastore = UserManager(requireContext())

        if (requireContext().getSharedPreferences("Challenge", Context.MODE_PRIVATE).contains("EMAIL")){
            btn_login.setOnClickListener {
//            SHARED PREFERENCES
                val inputemail = et_masukkan_email_login.text.toString()
                val inputpassword = et_masukkan_password_login.text.toString()

                var email = ""
                var password = ""

                GlobalScope.launch {
                    userdatastore.saveData(null.toString(), inputemail, inputpassword)
                }

                //        Baca & ambil data nama dari datastore
                userdatastore.userEmail.asLiveData().observe(requireActivity(),{
                    email = it.toString()
                })

                //        Baca & ambil data nama dari datastore
                userdatastore.userPassword.asLiveData().observe(requireActivity(),{
                    password = it.toString()
                })


                val loginauth = if (inputemail != email || inputpassword != password){
                    Toast.makeText(requireContext(), "Email/Password Anda Salah, Silahkan Login Kembali", Toast.LENGTH_SHORT).show()
                    false
                }else{
                    true
                }

//            INTENT FRAGMENT
                if (loginauth == true){
                    Navigation.findNavController(view).navigate(R.id.action_loginNoteFragment_to_homeNoteFragment)
                }
            }
        } else{
            Toast.makeText(requireContext(), "Anda Belum Memiliki Akun, Silahkan Register Terlebih Dahulu", Toast.LENGTH_SHORT).show()
        }


        tv_buat_akun.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginNoteFragment_to_registerFragment)
        }
    }

}