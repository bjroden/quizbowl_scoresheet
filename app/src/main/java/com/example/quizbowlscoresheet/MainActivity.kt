package com.example.quizbowlscoresheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.quizbowlscoresheet.database.models.Game
import com.example.quizbowlscoresheet.database.models.TeamAnswered
import com.example.quizbowlscoresheet.database.models.Tossup

class MainActivity : AppCompatActivity() {
    val mainActivityViewModel: MainActivityViewModel by viewModels {
        MainActivityViewModel.TossupViewModelFactory((application as QuizbowlApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // TODO: Testing insertion. This should be replaced with GameAGQBA
        for (i in 1..2) {
            val tossups = List(5) { j ->
                Tossup(
                    null,
                    null,
                    j,
                    1,
                    TeamAnswered.NONE,
                    "Hello world!"
                )
            }
            val game = Game(null)
            mainActivityViewModel.insert(game)
        }
    }
}