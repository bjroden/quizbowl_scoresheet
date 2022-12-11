package com.example.quizbowlscoresheet

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.textfield.TextInputEditText

class GameOverviewActivity : AppCompatActivity() {

    lateinit var viewModel: GameOverviewViewModel
    lateinit var quarter1Nav: TextView
    lateinit var quarter2Nav: TextView
    lateinit var quarter3Nav: TextView
    lateinit var quarter4Nav: TextView
    lateinit var team1NameView: EditText
    lateinit var team2NameView: EditText

    val startGameQuarterActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode== Activity.RESULT_OK){
            Log.d("MainActivity","Completed")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_overview)

        val scoreQ1T1View = findViewById<TextInputEditText>(R.id.overviewScoreQ1T1)
        val scoreQ2T1View = findViewById<TextInputEditText>(R.id.overviewScoreQ2T1)
        val scoreQ3T1View = findViewById<TextInputEditText>(R.id.overviewScoreQ3T1)
        val scoreQ4T1View = findViewById<TextInputEditText>(R.id.overviewScoreQ4T1)
        val scoreTotalT1View = findViewById<TextInputEditText>(R.id.overviewScoreTotalT1)

        val scoreQ1T2View = findViewById<TextInputEditText>(R.id.overviewScoreQ1T2)
        val scoreQ2T2View = findViewById<TextInputEditText>(R.id.overviewScoreQ2T2)
        val scoreQ3T2View = findViewById<TextInputEditText>(R.id.overviewScoreQ3T2)
        val scoreQ4T2View = findViewById<TextInputEditText>(R.id.overviewScoreQ4T2)
        val scoreTotalT2View = findViewById<TextInputEditText>(R.id.overviewScoreTotalT2)

        team1NameView = findViewById(R.id.TeamAName)
        team2NameView = findViewById(R.id.TeamBName)

        val gameId = intent.getLongExtra(StaticTags.GAME_ID_TAG, -1)
        if (gameId == -1L) {
            TODO("Do better error checking for incorrect game id")
        }

        // TODO: initialize with factory when we can figure out what's causing the runtime errors on multiple constructor fields
        // Words cannot express how much I hate JVM languages
        viewModel = GameOverviewViewModel((application as QuizbowlApplication).repository, gameId)
        team1NameView.setOnFocusChangeListener { _, focused ->
            if (!focused) { viewModel.changeTeam1Name(team1NameView.text.toString()) }
        }
        team2NameView.setOnFocusChangeListener { _, focused ->
            if (!focused) { viewModel.changeTeam2Name(team2NameView.text.toString()) }
        }
        viewModel.currentGame.observe(this) { game ->
            scoreQ1T1View.setText(game.team1Round1Score.toString())
            scoreQ2T1View.setText(game.team1Round2Score.toString())
            scoreQ3T1View.setText(game.team1Round3Score.toString())
            scoreQ4T1View.setText(game.team1Round4Score.toString())
            scoreTotalT1View.setText(game.team1TotalScore.toString())

            scoreQ1T2View.setText(game.team2Round1Score.toString())
            scoreQ2T2View.setText(game.team2Round2Score.toString())
            scoreQ3T2View.setText(game.team2Round3Score.toString())
            scoreQ4T2View.setText(game.team2Round4Score.toString())
            scoreTotalT2View.setText(game.team2TotalScore.toString())

            team1NameView.setText(game.team1?.name ?: "")
            team2NameView.setText(game.team2?.name ?: "")
        }

        var quarterIntent = Intent(this,GameQuarterActivity::class.java)
        quarterIntent.putExtra(StaticTags.GAME_ID_TAG, gameId)

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

    override fun onStop() {
        super.onStop()
        viewModel.changeTeam1Name(team1NameView.text.toString())
        viewModel.changeTeam2Name(team2NameView.text.toString())
    }
}