package com.macluczak.a2health.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.macluczak.a2health.Interface.UserLogInterface
import com.macluczak.a2health.Models.UserModel
import com.macluczak.a2health.R
import com.macluczak.a2health.ViewModels.LoginViewModel
import com.macluczak.a2health.databinding.FragmentLoginBinding

class LoginFragment() : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var user: UserModel? = null
    private val viewModel: LoginViewModel by viewModels()

    private val userInterface: UserLogInterface
        get() = requireActivity() as UserLogInterface

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        binding.loginErrorView.text = ""
        user = arguments?.getParcelable("user") as? UserModel?
        if (user != null) {
            binding.loginUsernameInput.setText(user!!.username.toString())
        }

        binding.loginButton.setOnClickListener {

            binding.loginErrorView.text = ""

            val username = binding.loginUsernameInput.text.toString()
            val password = binding.loginPasswordInput.text.toString()

            if (username.isNotBlank() && password.isNotBlank()) {
                binding.progressbarlogin.visibility = View.VISIBLE

                viewModel.loginToAccount(username, password) {

                    if (it != null) it.username?.let { name -> userInterface.logIn(name) }
                    else binding.loginErrorView.text = "invalid data"

                    binding.progressbarlogin.visibility = View.GONE
                }
            }
            else {
                binding.loginErrorView.text = "Provide all data"
            }

        }

        binding.createAccount.setOnClickListener {
            val registerFragment = RegisterFragment()
            childFragmentManager.beginTransaction().apply {
                replace(R.id.loginLayout, registerFragment)
                commit()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(user: UserModel?) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("user", user)
                }
            }
    }


}