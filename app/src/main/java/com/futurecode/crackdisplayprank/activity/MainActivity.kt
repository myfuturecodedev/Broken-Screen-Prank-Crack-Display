package com.futurecode.crackdisplayprank.activity

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private var navController: NavController? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navController = navHostFragment!!.navController

    }

    fun goToMain() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish() // Launcher activity context dismiss ho jayega pre-login execution cycle se
    }
}

