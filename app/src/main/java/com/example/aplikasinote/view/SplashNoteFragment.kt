package com.example.aplikasinote.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.aplikasinote.R

class SplashNoteFragment : Fragment() {

    lateinit var sharedpreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            sharedpreferences = requireContext().getSharedPreferences("Challenge", Context.MODE_PRIVATE)

            if (requireContext().getSharedPreferences("Challenge", Context.MODE_PRIVATE).contains("EMAIL")
                && requireContext().getSharedPreferences("Challenge", Context.MODE_PRIVATE).contains("PASSWORD")){
                Navigation.findNavController(view).navigate(R.id.action_splashNoteFragment_to_loginNoteFragment)
            }else{
                Navigation.findNavController(view).navigate(R.id.action_splashNoteFragment_to_loginNoteFragment)
            }
        }, 3000)

    }
}