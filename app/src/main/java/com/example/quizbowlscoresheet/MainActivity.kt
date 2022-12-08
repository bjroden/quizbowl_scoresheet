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
    lateinit var loadGameBtn: Button

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
        loadGameBtn = findViewById(R.id.load_game_btn)
        newGameBtn.setOnClickListener {
            Log.d("MainActivity", "new game button was clicked")
            startGameOverviewActivity.launch(Intent(this, GameOverviewActivity::class.java))
        }
        loadGameBtn.setOnClickListener {
            Log.d("MainActivity", "load game button was clicked")
            startGameOverviewActivity.launch(Intent(this, LoadGameActivity::class.java))
        }
        

        // TODO: replace this with something real
        mainActivityViewModel.newGameAGQBA()
        mainActivityViewModel.allGameAGQBA.observe(this) { games ->
            for (game in games) {
                Log.d("MainActivity", game.toString())
            }
        }
    }
}