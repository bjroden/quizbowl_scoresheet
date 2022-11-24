package com.example.quizbowlscoresheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.quizbowlscoresheet.database.models.Game
import com.example.quizbowlscoresheet.database.models.GameAGQBA
import com.example.quizbowlscoresheet.database.models.TeamAnswered
import com.example.quizbowlscoresheet.database.models.Tossup

class MainActivity : AppCompatActivity() {
    val mainActivityViewModel: MainActivityViewModel by viewModels {
        MainActivityViewModel.TossupViewModelFactory((application as QuizbowlApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // TODO: replace this with something real
        mainActivityViewModel.newGameAGQBA()
        mainActivityViewModel.allGameAGQBA.observe(this) { games ->
            for (game in games) {
                Log.d("MainActivity", game.toString())
            }
        }
    }
}