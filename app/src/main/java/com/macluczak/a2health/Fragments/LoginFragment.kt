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
import com.macluczak.a2health.ViewModels.LoginViewModel
import com.macluczak.a2health.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var user: UserModel? = null
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        user = arguments?.getParcelable("user") as? UserModel?
        if (user != null) {
            binding.loginUsernameInput.setText(user!!.username.toString())
        }
        binding.loginButton.setOnClickListener {
            val username = binding.loginUsernameInput.text.toString()
            val password = binding.loginPasswordInput.text.toString()
            if (username.isNotBlank() && password.isNotBlank()) {
                viewModel.loginToAccount(username, password)
            } else {
                Toast.makeText(requireContext(), "Provide all data", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.ifLogged.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Username or password is incorrect", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.userAccount.observe(viewLifecycleOwner) {
            //TUTAJ MASZ USERMODEL CO DALEJ Z TYM ZROBISZ TO NIE WIEM XD
            Toast.makeText(requireContext(), "${it.username}", Toast.LENGTH_SHORT).show()
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