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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val quarter = intent.getIntExtra("quarter", 0)
        when(quarter){
            1,4 -> {
                setContentView(R.layout.activity_game_tossup)
                val adapter = TossupAdapter(this::questionAnswered)
                val recyclerView = findViewById<RecyclerView>(R.id.questionRecycler)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

                //replace tossupList with the actual list of questions
                var tossupList = listOf<Tossup>(
                    Tossup(1,1, 1, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(2,1, 2, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(3,1, 3, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(4,1, 4, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(5,1, 5, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(6,1, 6, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(7,1, 7, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(8,1, 8, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(9,1, 9, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(10,1, 10, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(11,1, 11, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(12,1, 12, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(13,1, 13, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(14,1, 14, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(15,1, 15, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(16,1, 16, 1, TeamAnswered.TEAM2, "lib"),
                    Tossup(17,1, 17, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(18,1, 18, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(19,1, 19, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(20,1, 20, 1, TeamAnswered.TEAM1, "lib")
                )
                adapter.submitList(tossupList)
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

    }

    private fun questionAnswered(lightningQuestion: LightningQuestion){

    }
}