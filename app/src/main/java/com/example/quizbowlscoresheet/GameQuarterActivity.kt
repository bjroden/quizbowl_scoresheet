package com.example.quizbowlscoresheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizbowlscoresheet.database.models.*
import com.google.android.material.textfield.TextInputEditText

class GameQuarterActivity : AppCompatActivity() {

    private lateinit var viewModel: GameQuarterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gameId = intent.getLongExtra(StaticTags.GAME_ID_TAG, -1)
        if (gameId == -1L) {
            TODO("Do better error checking for incorrect game id")
        }
        // TODO: initialize with factory when we can figure out what's causing the runtime errors on multiple constructor fields
        viewModel = GameQuarterViewModel((application as QuizbowlApplication).repository, gameId)

        val quarter = intent.getIntExtra("quarter", 0)
        when(quarter){
            // TODO: gamer fix 2 and 5 pls
            1,4 -> {
                setContentView(R.layout.activity_game_tossup)
                val adapter = TossupAdapter(this::questionAnswered)
                val recyclerView = findViewById<RecyclerView>(R.id.questionRecycler)
                val quarterTitle = findViewById<TextView>(R.id.quarterTitle)
                quarterTitle.text = quarterTitle.text.toString() + quarter.toString()
                recyclerView.adapter = adapter
                recyclerView.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
                val team1ScoreView = findViewById<TextInputEditText>(R.id.tossupRoundScoreTeam1)
                val team2ScoreView = findViewById<TextInputEditText>(R.id.tossupRoundScoreTeam2)

                viewModel.currentGame.observe(this) { game ->
                    if (quarter == 1) {
                        adapter.submitList(game.round1Tossups)
                        team1ScoreView.setText(game.team1Round1Score.toString())
                        team2ScoreView.setText(game.team2Round1Score.toString())
                    }
                    else {
                        adapter.submitList(game.round4Tossups)
                        team1ScoreView.setText(game.team1Round4Score.toString())
                        team2ScoreView.setText(game.team2Round4Score.toString())
                    }
                }
//                recyclerView.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
            }
            2 -> {
                setContentView(R.layout.activity_game_bonus)
                val adapter = TossupAdapter(this::questionAnswered)
                val recyclerView = findViewById<RecyclerView>(R.id.questionRecycler)
                recyclerView.adapter = adapter
                val team1ScoreView = findViewById<TextInputEditText>(R.id.bonusRoundScoreTeam1)
                val team2ScoreView = findViewById<TextInputEditText>(R.id.bonusRoundScoreTeam2)
                recyclerView.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

                val bonusAdapter = BonusAdapter(this::bonusAnswered)
                val bonusRecyclerView = findViewById<RecyclerView>(R.id.bonusRecycler)
                bonusRecyclerView.adapter = bonusAdapter
                bonusRecyclerView.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

                viewModel.currentGame.observe(this) { game ->
                    adapter.submitList(game.round2Tossups)
                    bonusAdapter.submitList(game.bonusCategories)
                    team1ScoreView.setText(game.team1Round2Score.toString())
                    team2ScoreView.setText(game.team2Round2Score.toString())
                }

            }
            3 -> {
                setContentView(R.layout.activity_game_lightning)
                val adapterA = LightningAdapter(this::lightningQuestionAnswered)
                val adapterB = LightningAdapter(this::lightningQuestionAnswered)
                val recyclerViewA = findViewById<RecyclerView>(R.id.questionRecyclerA)
                val recyclerViewB = findViewById<RecyclerView>(R.id.questionRecyclerB)
                recyclerViewA.adapter = adapterA
                recyclerViewB.adapter = adapterB
                recyclerViewA.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
                recyclerViewB.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

                val team1ScoreView = findViewById<TextInputEditText>(R.id.lightningRoundScoreTeam1)
                val team2ScoreView = findViewById<TextInputEditText>(R.id.lightningRoundScoreTeam2)

                viewModel.currentGame.observe(this) { game ->
                    team1ScoreView.setText(game.team1Round3Score.toString())
                    team2ScoreView.setText(game.team2Round3Score.toString())
                    game.team1LightningRound?.let { adapterA.submitList(it.questions) }
                    game.team2LightningRound?.let { adapterB.submitList(it.questions) }
                }
            }
        }



    }

    private fun questionAnswered(tossup: Tossup){
        viewModel.updateTossup(tossup)
    }

    private fun round2TossupAnswered(tossup: Tossup) {
        viewModel.updateTossup(tossup)
    }

    private fun lightningQuestionAnswered(lightningQuestion: LightningQuestion) {
        viewModel.updateLightningQuestion(lightningQuestion)
    }


    private fun bonusAnswered(bonusCategory: BonusCategory){
        //TODO: MOM
    }
}