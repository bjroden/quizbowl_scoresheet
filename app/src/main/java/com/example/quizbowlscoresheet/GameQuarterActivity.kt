package com.example.quizbowlscoresheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizbowlscoresheet.database.models.LightningAnswer
import com.example.quizbowlscoresheet.database.models.LightningQuestion
import com.example.quizbowlscoresheet.database.models.TeamAnswered
import com.example.quizbowlscoresheet.database.models.Tossup
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
            1,4 -> {
                setContentView(R.layout.activity_game_tossup)
                val adapter = TossupAdapter(this::questionAnswered)
                val recyclerView = findViewById<RecyclerView>(R.id.questionRecycler)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = GridLayoutManager(this, 4, RecyclerView.HORIZONTAL, false)
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
                recyclerView.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
            }
            2 -> {
                setContentView(R.layout.activity_game_bonus)
//                val adapter = BonusAdapter(this::questionAnswered)
//                val recyclerView = findViewById<RecyclerView>(R.id.questionRecycler)
//                recyclerView.adapter = adapter
//                recyclerView.layoutManager = GridLayoutManager(this, 4, RecyclerView.HORIZONTAL, false)
//                var tossupList = listOf<Tossup>(
//                    Tossup(1,1, 1, 1, TeamAnswered.TEAM1, "lib"),
//                    Tossup(2,1, 2, 1, TeamAnswered.TEAM1, "lib"),
//                    Tossup(3,1, 3, 1, TeamAnswered.TEAM1, "lib"),
//                    Tossup(4,1, 4, 1, TeamAnswered.TEAM1, "lib")
//                )
//                adapter.submitList(tossupList)
            }
            3 -> {
                setContentView(R.layout.activity_game_lightning)
                val adapterA = LightningAdapter(this::questionAnswered)
                val adapterB = LightningAdapter(this::questionAnswered)
                val recyclerViewA = findViewById<RecyclerView>(R.id.questionRecyclerA)
                val recyclerViewB = findViewById<RecyclerView>(R.id.questionRecyclerB)
                recyclerViewA.adapter = adapterA
                recyclerViewB.adapter = adapterB
                recyclerViewA.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
                recyclerViewB.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

                //replace lightningList with the actual list of questions from the
                var lightningList = listOf<LightningQuestion>(
                    LightningQuestion(1,1, LightningAnswer.CORRECT, ""),
                    LightningQuestion(2,2, LightningAnswer.INCORRECT, ""),
                    LightningQuestion(3,3, LightningAnswer.BOUNCED_BACK, ""),
                    LightningQuestion(4,4, LightningAnswer.STALLED, ""),
                    LightningQuestion(5,5, LightningAnswer.BOUNCED_BACK, ""),
                    LightningQuestion(6,6, LightningAnswer.STALLED, "")
                )
                adapterA.submitList(lightningList)
                adapterB.submitList(lightningList)
            }
        }



    }

    private fun questionAnswered(tossup: Tossup){
        viewModel.updateTossup(tossup)
    }

    private fun questionAnswered(lightningQuestion: LightningQuestion){

    }
}