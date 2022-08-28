package com.macluczak.a2health.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.macluczak.a2health.Models.UserModel
import com.macluczak.a2health.R
import com.macluczak.a2health.ViewModels.RegisterViewModel
import com.macluczak.a2health.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

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
        binding.errorView.text = ""
        binding.registerButton.setOnClickListener {
            binding.errorView.text = ""
            val username = binding.registerUsernameInput.text.toString()
            val password = binding.registerPasswordInput.text.toString()
            val passwordConfirm = binding.registerConfirmPasswordInput.text.toString()
            if (username.isNotBlank() && password.isNotBlank() && passwordConfirm.isNotBlank()) {
                if (password == passwordConfirm) {
                    viewModel.createAccount(username, password)
                } else {
                    binding.errorView.text = "Passwords don't match"
                }
            } else {
                binding.errorView.text = "Provide all data"
            }
        }
        viewModel.usernameAvailable.observe(viewLifecycleOwner) {
            if (!it) {
                binding.errorView.text = "Username is taken"
            }
        }

        viewModel.createdUser.observe(viewLifecycleOwner) {
            if (it != null) {
                goToLoginPage(viewModel.createdUser.value)
            } else {
                binding.errorView.text = "Something went wrong"
            }
        }

        binding.haveAccountButton.setOnClickListener{
            goToLoginPage(null)
        }
    }

    private fun goToLoginPage(user: UserModel?) {
        val loginFragment = LoginFragment.newInstance(user)
        childFragmentManager.beginTransaction().apply {
            replace(R.id.registerLayout, loginFragment)
            commit()
        }
    }



}