package com.example.quizbowlscoresheet

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels

class GameOverviewActivity : AppCompatActivity() {

    lateinit var quarter1Nav: TextView
    lateinit var quarter2Nav: TextView
    lateinit var quarter3Nav: TextView
    lateinit var quarter4Nav: TextView

    val startGameQuarterActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode== Activity.RESULT_OK){
            Log.d("MainActivity","Completed")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_overview)

        val gameId = intent.getLongExtra(MainActivity.GAME_ID_TAG, -1)
        if (gameId == -1L) {
            TODO("Do better error checking for incorrect game id")
        }

        // TODO: initialize with factory when we can figure out what's causing the runtime errors on multiple constructor fields
        // Words cannot express how much I hate JVM languages
        val viewModel = GameOverviewViewModel((application as QuizbowlApplication).repository, gameId)
        viewModel.currentGame.observe(this) {
            // TODO: Populate fields here
            Log.d("GameOverviewActivity", it.toString())
        }

        var quarterIntent = Intent(this,GameQuarterActivity::class.java)
        quarter1Nav = findViewById(R.id.Quarter1Title)
        quarter1Nav.setOnClickListener{
            quarterIntent.putExtra("quarter", 1)
            startGameQuarterActivity.launch(quarterIntent)
        }

        quarter2Nav = findViewById(R.id.Quarter2Title)
        quarter2Nav.setOnClickListener{
            quarterIntent.putExtra("quarter", 2)
            startGameQuarterActivity.launch(quarterIntent)
        }

        quarter3Nav = findViewById(R.id.Quarter3Title)
        quarter3Nav.setOnClickListener{
            quarterIntent.putExtra("quarter", 3)
            startGameQuarterActivity.launch(quarterIntent)
        }

        quarter4Nav = findViewById(R.id.Quarter4Title)
        quarter4Nav.setOnClickListener{
            quarterIntent.putExtra("quarter", 4)
            startGameQuarterActivity.launch(quarterIntent)
        }
    }
}