package com.example.retrofitroomsample.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.retrofitroomsample.R
import com.example.retrofitroomsample.binding.viewBinding
import com.example.retrofitroomsample.databinding.FragmentLoginBinding
import com.example.retrofitroomsample.util.KeyboardUtils
import com.example.retrofitroomsample.util.setErrorListener
import com.example.retrofitroomsample.util.showError
import com.example.retrofitroomsample.viewmodel.DataViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    private val viewModel: DataViewModel by activityViewModels()

    @Inject
    lateinit var keyboard: KeyboardUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtEmail = binding.txtEmail
        val txtPassword = binding.txtPassword
        val btnLogin = binding.btnLogin
        val btnRegister = binding.btnRegister

        txtEmail.setErrorListener()
        txtPassword.setErrorListener()

        btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }

        btnLogin.setOnClickListener {
            keyboard.hide(this)

            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()
            var fail = false

            if (email.isEmpty()) {
                txtEmail.showError(getString(R.string.txt_enter_email))
                fail = true
            }
            if (password.isEmpty()) {
                txtPassword.showError(getString(R.string.txt_enter_password))
                fail = true
            }

            if (fail) return@setOnClickListener

            viewModel.login(email, password).observe(viewLifecycleOwner) { result ->
                if (result) findNavController().navigate(R.id.action_auth_to_home)
                else {
                    Snackbar.make(view, "Invalid credentials!", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

}