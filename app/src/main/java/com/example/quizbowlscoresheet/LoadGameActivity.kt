package com.example.quizbowlscoresheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizbowlscoresheet.database.models.Game
import com.example.quizbowlscoresheet.database.models.TeamAnswered
import com.example.quizbowlscoresheet.database.models.Tossup

class LoadGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_game)
        val adapter = GameAdapter(this::gameSelected)
        val recyclerView = findViewById<RecyclerView>(R.id.GameRecycler)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

        var gameList = listOf<Game>(
            Game(1,1,2),
            Game(2,1,2),
            Game(3,1,2),
            Game(4,1,2)
        )
        adapter.submitList(gameList)
    }

    private fun gameSelected(game: Game){
        Log.d("LoadGameActivity", "gay game" + game.id.toString())
    }
}