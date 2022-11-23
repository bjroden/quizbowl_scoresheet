package com.example.quizbowlscoresheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var newGameBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newGameBtn = findViewById(R.id.new_game_btn)
        newGameBtn.setOnClickListener{
            Log.d("MainActivity", "new game button was clicked")
        }
    }
}