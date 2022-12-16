package com.example.quizbowlscoresheet

import android.app.Activity
import android.app.AlertDialog
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
import com.google.android.material.appbar.MaterialToolbar

class LoadGameActivity : AppCompatActivity() {
    val viewModel: LoadGameViewModel by viewModels {
        LoadGameViewModel.LoadGameViewModelFactory((application as QuizbowlApplication).repository)
    }

    val startGameOverviewActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d("MainActivity", "Completed")
            }
        }

    private var selectionMode = SelectionMode.LAUNCH
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_game)
        val adapter = GameWithTeamAdapter(this::gameSelected)
        val recyclerView = findViewById<RecyclerView>(R.id.GameRecycler)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

        supportActionBar?.hide()
        toolbar = findViewById(R.id.loadGameToolbar)
        toolbar.inflateMenu(R.menu.load_activity_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete_game -> {
                    setMode(SelectionMode.DELETE)
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }
        }
        setMode(SelectionMode.LAUNCH)

        viewModel.gameList.observe(this) { games ->
            adapter.submitList(games)
        }
    }

    private fun gameSelected(game: Game){
        when (selectionMode) {
            SelectionMode.LAUNCH -> launchGame(game)
            SelectionMode.DELETE -> deleteGame(game)
        }
        setMode(SelectionMode.LAUNCH)
    }

    private fun launchGame(game: Game) {
        val intent = Intent(this, GameOverviewActivity::class.java)
        intent.putExtra(StaticTags.GAME_ID_TAG, game.id)
        startGameOverviewActivity.launch(intent)
    }

    private fun deleteGame(game: Game) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.confirm_delete_title))
            .setMessage(getString(R.string.confirm_delete_body))
            .setPositiveButton(getString(R.string.yes)) { _, _ -> viewModel.deleteGame(game) }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }

    private fun setMode(mode: SelectionMode) {
        when (mode) {
            SelectionMode.LAUNCH -> toolbar.title = getString(R.string.app_name)
            SelectionMode.DELETE -> toolbar.title = getString(R.string.toolbar_delete_mode)
        }
        selectionMode = mode
    }

    private enum class SelectionMode {
        LAUNCH,
        DELETE
    }
}