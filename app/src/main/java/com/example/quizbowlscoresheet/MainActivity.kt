package com.example.quizbowlscoresheet

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    val mainActivityViewModel: MainActivityViewModel by viewModels {
        MainActivityViewModel.TossupViewModelFactory((application as QuizbowlApplication).repository)
    }

    lateinit var newGameBtn: Button

    val startGameOverviewActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d("MainActivity", "Completed")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newGameBtn = findViewById(R.id.new_game_btn)
        newGameBtn.setOnClickListener {
            mainActivityViewModel.newGameAGQBA { gameId ->
                val intent = Intent(this, GameOverviewActivity::class.java)
                intent.putExtra(StaticTags.GAME_ID_TAG, gameId)
                startGameOverviewActivity.launch(intent)
            }
        }
    }
}