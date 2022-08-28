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
import kotlin.properties.Delegates

class LoginFragment() : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private var user: UserModel? = null
    private lateinit var userName: String
    private var isLogged by Delegates.notNull<Boolean>()
    private val viewModel: LoginViewModel by viewModels()

    private val userInterface: UserLogInterface
        get() = requireActivity() as UserLogInterface

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        userName = userInterface.getLoggedUser()
        isLogged = checkIsUserLogged()
        binding.loginErrorView.text = ""

        user = arguments?.getParcelable("user") as? UserModel?
        if (user != null) {
            binding.loginUsernameInput.setText(user!!.username.toString())
        }

        binding.loginButton.setOnClickListener {

            binding.loginErrorView.text = ""
            if (!isLogged) {
                val username = binding.loginUsernameInput.text.toString()
                val password = binding.loginPasswordInput.text.toString()

                if (username.isNotBlank() && password.isNotBlank()) {
                    binding.progressbarlogin.visibility = View.VISIBLE

                    viewModel.loginToAccount(username, password) {

                        if (it != null) it.username?.let { name -> userInterface.logIn(name)
                            userName = it.username
                            isLogged = checkIsUserLogged()
                        }
                        else binding.loginErrorView.text = "invalid data"

                        binding.progressbarlogin.visibility = View.GONE
                    }
                } else {
                    binding.loginErrorView.text = "Provide all data"
                }
            } else {

                userInterface.logOut()
                isLogged = false

                binding.loginButton.text = "Login"
                binding.loginText.text = "Login to your account"
                binding.loginUsernameInput.apply {
                    visibility = View.VISIBLE
                    text.clear()
                }
                binding.loginPasswordInput.apply {
                    visibility = View.VISIBLE
                    text.clear()
                }
                binding.createAccountButton.visibility = View.VISIBLE
            }
        }
        binding.createAccountButton.setOnClickListener {
            val registerFragment = RegisterFragment()
            childFragmentManager.beginTransaction().apply {
                replace(R.id.loginLayout, registerFragment)
                commit()
            }
        }
    }

    private fun checkIsUserLogged(): Boolean = if (userName.isNotBlank()) {
        binding.loginButton.text = "Log out"
        binding.loginText.text = "Log out user $userName"
        binding.createAccountButton.visibility = View.GONE
        binding.loginUsernameInput.visibility = View.GONE
        binding.loginPasswordInput.visibility = View.GONE
        true
    } else false

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