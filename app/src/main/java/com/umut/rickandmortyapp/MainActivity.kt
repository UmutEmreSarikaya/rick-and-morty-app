package com.umut.rickandmortyapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.umut.rickandmortyapp.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val isFirstTime = sharedPref.getBoolean(getString(R.string.first_time_boolean_key), true)

        if(isFirstTime){
            sharedPref.edit().putBoolean(getString(R.string.first_time_boolean_key), false).apply()
            binding.welcomeText.text = getString(R.string.welcome_text)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }

}