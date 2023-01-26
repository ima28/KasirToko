package com.example.kasirtoko.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.kasirtoko.MainActivity
import com.example.kasirtoko.databinding.ActivityLoginBinding
import com.example.kasirtoko.utils.longToast
import com.example.kasirtoko.utils.shortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.ReadOnlyProperty

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by viewBindings()

    private fun viewBindings(): ReadOnlyProperty<LoginActivity, ActivityLoginBinding> {
        TODO("Not yet implemented")
    }

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loginViewModel.loginViewModel.observe(this) {
            binding.btnLogin.isEnabled = true
            when (it) {
                is LoginViewModel.State.Failed -> {
                    longToast(it.message)
                }
                is LoginViewModel.State.Loading -> {
                    binding.btnLogin.isEnabled = false
                }
                is LoginViewModel.State.Success -> {
                    startActivity(Intent(applicationContext, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }).also {
                        finish()
                    }
                    shortToast(it.message)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        with(binding) {

            btnLogin.setOnClickListener {
                val email = tilEmail.editText?.text.toString().trim()
                val password = tilPassword.editText?.text.toString().trim()

                loginViewModel.login(email, password)
            }
        }
    }
}