package com.inforcap.backgroundservice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.inforcap.backgroundservice.databinding.ActivityMainBinding
import com.inforcap.backgroundservice.service.BackgroundService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.textViewHelloWorld.setOnClickListener {
            Log.i("info", "onCreate: HOLA")
            Intent(this, BackgroundService::class.java).also {
                startService(it)
            }
        }


    }
}