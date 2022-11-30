package com.example.quizbowlscoresheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GameQuarterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_game_quarter)
        val quarter = intent.getIntExtra("quarter", 0)
        when(quarter){
            1 -> setContentView(R.layout.activity_game_quarter_1)
            2 -> setContentView(R.layout.activity_game_quarter_2)
            3 -> setContentView(R.layout.activity_game_quarter_3)
            4 -> setContentView(R.layout.activity_game_quarter_4)
            else -> setContentView(R.layout.activity_game_quarter)
        }

    }
}