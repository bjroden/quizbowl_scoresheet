package com.example.quizbowlscoresheet

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.textfield.TextInputEditText

class GameOverviewActivity : AppCompatActivity() {

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
        val viewModel: GameOverviewViewModel by viewModels {
            GameOverviewViewModel.GameOverviewViewModelFactory((application as QuizbowlApplication).repository, gameId)
        }

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

        quarter1Nav = findViewById(R.id.Quarter1Title)
        quarter1Nav.setOnClickListener { launchGameQuarter(1, gameId) }
        scoreQ1T1View.setOnClickListener { launchGameQuarter(1, gameId) }
        scoreQ1T2View.setOnClickListener { launchGameQuarter(1, gameId) }
        findViewById<LinearLayout>(R.id.Q1ScoreContainer).setOnClickListener { launchGameQuarter(1, gameId) }

        quarter2Nav = findViewById(R.id.Quarter2Title)
        quarter2Nav.setOnClickListener { launchGameQuarter(2, gameId) }
        scoreQ2T1View.setOnClickListener { launchGameQuarter(2, gameId) }
        scoreQ2T2View.setOnClickListener { launchGameQuarter(2, gameId) }
        findViewById<LinearLayout>(R.id.Q2ScoreContainer).setOnClickListener { launchGameQuarter(2, gameId) }

        quarter3Nav = findViewById(R.id.Quarter3Title)
        quarter3Nav.setOnClickListener { launchGameQuarter(3, gameId) }
        scoreQ3T1View.setOnClickListener { launchGameQuarter(3, gameId) }
        scoreQ3T2View.setOnClickListener { launchGameQuarter(3, gameId) }
        findViewById<LinearLayout>(R.id.Q3ScoreContainer).setOnClickListener { launchGameQuarter(3, gameId) }

        quarter4Nav = findViewById(R.id.Quarter4Title)
        quarter4Nav.setOnClickListener { launchGameQuarter(4, gameId) }
        scoreQ4T1View.setOnClickListener { launchGameQuarter(4, gameId) }
        scoreQ4T2View.setOnClickListener { launchGameQuarter(4, gameId) }
        findViewById<LinearLayout>(R.id.Q4ScoreContainer).setOnClickListener { launchGameQuarter(4, gameId) }
    }

    private fun launchGameQuarter(quarter: Int, gameId: Long) {
        val quarterIntent = Intent(this,GameQuarterActivity::class.java)
        quarterIntent.putExtra(StaticTags.GAME_ID_TAG, gameId)
        quarterIntent.putExtra(StaticTags.GAME_QUARTER, quarter)
        startGameQuarterActivity.launch(quarterIntent)
    }

    override fun onStop() {
        super.onStop()
        team1NameView.clearFocus()
        team2NameView.clearFocus()
    }
}