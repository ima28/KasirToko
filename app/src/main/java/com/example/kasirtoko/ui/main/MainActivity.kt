package com.example.kasirtoko.ui.main

import android.content.Intent
import android.os.Bundle
import android.se.omapi.Session
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.kasirtoko.R
import com.example.kasirtoko.databinding.ActivityMainBinding
import com.example.kasirtoko.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.ReadOnlyProperty

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBindings()

    private fun viewBindings(): ReadOnlyProperty<MainActivity, ActivityMainBinding> {
        TODO("Not yet implemented")
    }

    private val navController: NavController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment
        navHostFragment.navController
    }

    @Inject
    lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (session.isLogin() as Boolean) startActivity(Intent(applicationContext, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }).also {
            finish()
            return
        }

        setContentView(binding.root)

        with(binding) {
            toolbar.setupWithNavController(navController, AppBarConfiguration(
                setOf(
                    R.id.productFragment,
                    R.id.transactionFragment,
                    R.id.reportFragment,
                    R.id.aboutFragment,
                )
            ))
            //this.bottomNav.setupWithNavController(navController)
        }
    }
}

private fun Any.setupWithNavController(navController: NavController) {
    TODO("Not yet implemented")
}

private fun Session.isLogin(): Any {
    TODO("Not yet implemented")
}
