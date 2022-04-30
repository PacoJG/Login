package com.example.login.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.login.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    lateinit var autentication:FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        autentication = FirebaseAuth.getInstance()

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.loginButton.setOnClickListener {
            login()
        }


        return root
    }

    override fun onStart() {
        super.onStart()
        val user:FirebaseUser? = autentication.currentUser
        val textView: TextView = binding.asegurar
        if(user==null){
            textView.text = "Usuario no ha iniciado sesion"
        }else{
            textView.text = "Usuario inicio sesion"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun login(){
        val correo: EditText = binding.userTxt
        val password: EditText = binding.passwordTxt
        //Log.i("Correo", correo.text.toString())
        //Log.i("Password", password.text.toString())
        autentication.signInWithEmailAndPassword(correo.text.toString(),password.text.toString()).addOnCompleteListener{
            if(it.isSuccessful){
                Message("usuario correcto")
            }else{
                Message("Usuario incorrecto")
            }
        }
    }
    private fun Message(message:String){
        Toast.makeText(context,"${message}", Toast.LENGTH_SHORT).show()
    }
}