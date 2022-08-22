package com.macluczak.a2health.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.macluczak.a2health.Models.UserModel
import com.macluczak.a2health.R
import com.macluczak.a2health.Repositories.LoginRepo
import com.macluczak.a2health.ViewModels.LoginViewModel
import com.macluczak.a2health.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        binding.registerButton.setOnClickListener {
            val username = binding.registerUsernameInput.text.toString()
            val password = binding.registerPasswordInput.text.toString()
            val passwordConfirm = binding.registerConfirmPasswordInput.text.toString()
            if (username.isNotBlank() && password.isNotBlank() && passwordConfirm.isNotBlank()) {
                if (password == passwordConfirm) {
                    Log.d("###########", "onViewCreated: kanfan")
                    viewModel.createAccount(username, password)
                }
            }
        }
    }

}