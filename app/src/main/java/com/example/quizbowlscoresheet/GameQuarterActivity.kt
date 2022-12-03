package com.example.quizbowlscoresheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
                recyclerView.layoutManager = GridLayoutManager(this, 4, RecyclerView.HORIZONTAL, false)
                var tossupList = listOf<Tossup>(
                    Tossup(1,1, 1, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(2,1, 2, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(3,1, 3, 1, TeamAnswered.TEAM1, "lib"),
                    Tossup(4,1, 4, 1, TeamAnswered.TEAM1, "lib")
                )
                adapter.submitList(tossupList)
            }
            2 -> setContentView(R.layout.activity_game_bonus)
            3 -> setContentView(R.layout.activity_game_lightning)
            else -> setContentView(R.layout.activity_game_quarter)
        }



    }

    private fun questionAnswered(tossup: Tossup){

    }
}