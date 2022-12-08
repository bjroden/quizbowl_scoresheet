package com.example.quizbowlscoresheet

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizbowlscoresheet.database.models.Game
import com.example.quizbowlscoresheet.database.models.TeamAnswered
import com.example.quizbowlscoresheet.database.models.Tossup

class LoadGameActivity : AppCompatActivity() {
    val startGameOverviewActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d("MainActivity", "Completed")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_game)
        val adapter = GameAdapter(this::gameSelected)
        val recyclerView = findViewById<RecyclerView>(R.id.GameRecycler)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

        // TODO: gamer
        var gameList = listOf<Game>(
            Game(1,1,2),
            Game(2,1,2),
            Game(3,1,2),
            Game(4,1,2)
        )
        adapter.submitList(gameList)
    }

    private fun gameSelected(game: Game){
        val intent = Intent(this, GameOverviewActivity::class.java)
        intent.putExtra(StaticTags.GAME_ID_TAG, game.id)
        startGameOverviewActivity.launch(intent)
        Log.d("LoadGameActivity", "gay game" + game.id.toString())
    }
}