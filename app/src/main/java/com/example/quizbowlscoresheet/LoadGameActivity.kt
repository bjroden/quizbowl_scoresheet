package com.example.quizbowlscoresheet

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizbowlscoresheet.database.models.Game

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
        val adapter = GameWithTeamAdapter(this::gameSelected)
        val recyclerView = findViewById<RecyclerView>(R.id.GameRecycler)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

        val viewModel: LoadGameViewModel by viewModels {
            LoadGameViewModel.LoadGameViewModelFactory((application as QuizbowlApplication).repository)
        }
        viewModel.gameList.observe(this) { games ->
            adapter.submitList(games)
        }
    }

    private fun gameSelected(game: Game){
        val intent = Intent(this, GameOverviewActivity::class.java)
        intent.putExtra(StaticTags.GAME_ID_TAG, game.id)
        startGameOverviewActivity.launch(intent)
        Log.d("LoadGameActivity", "gay game" + game.id.toString())
    }
}