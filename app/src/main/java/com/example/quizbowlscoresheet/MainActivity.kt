package com.example.quizbowlscoresheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.quizbowlscoresheet.database.models.TeamAnswered
import com.example.quizbowlscoresheet.database.models.Tossup

class MainActivity : AppCompatActivity() {
    // TODO: tossups should not be accessed directly, this is for temporary testing purposes
    val tossupViewModel: TossupViewModel by viewModels {
        TossupViewModel.TossupViewModelFactory((application as QuizbowlApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // TODO: Testing insertion
        for (i in 1..3) {
            val test = Tossup(null, TeamAnswered.NONE, "Hello world!")
            tossupViewModel.insert(test)
        }
    }
}