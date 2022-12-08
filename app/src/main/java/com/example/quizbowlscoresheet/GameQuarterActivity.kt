package com.example.quizbowlscoresheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizbowlscoresheet.database.models.LightningAnswer
import com.example.quizbowlscoresheet.database.models.LightningQuestion
import com.example.quizbowlscoresheet.database.models.TeamAnswered
import com.example.quizbowlscoresheet.database.models.Tossup

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

                viewModel.currentGame.observe(this) { game ->
                    adapter.submitList(game.roundNTossups(quarter))
                }
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
                recyclerViewA.layoutManager = GridLayoutManager(this, 4, RecyclerView.HORIZONTAL, false)
                recyclerViewB.layoutManager = GridLayoutManager(this, 4, RecyclerView.HORIZONTAL, false)

                //replace lightningList with the actual list of questions from the
                var lightningList = listOf<LightningQuestion>(
                    LightningQuestion(1,1, LightningAnswer.CORRECT, ""),
                    LightningQuestion(2,2, LightningAnswer.INCORRECT, ""),
                    LightningQuestion(3,3, LightningAnswer.BOUNCED_BACK, ""),
                    LightningQuestion(4,4, LightningAnswer.STALLED, "")
                )
                adapterA.submitList(lightningList)
                adapterB.submitList(lightningList)
            }
            else -> setContentView(R.layout.activity_game_quarter)
        }



    }

    private fun questionAnswered(tossup: Tossup){
        viewModel.updateTossup(tossup)
    }

    private fun questionAnswered(lightningQuestion: LightningQuestion){

    }
}