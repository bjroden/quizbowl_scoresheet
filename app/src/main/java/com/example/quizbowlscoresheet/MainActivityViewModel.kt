package com.example.quizbowlscoresheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import com.example.quizbowlscoresheet.database.repositories.agqbagame.GameAGQBARepository
import com.example.quizbowlscoresheet.database.models.*
import kotlinx.coroutines.launch

class MainActivityViewModel(private val gameAGQBARepository: GameAGQBARepository): ViewModel() {

    val allGameAGQBA = gameAGQBARepository.allGameAGQBA.asLiveData()

    fun insertGameAGQBA(game: GameAGQBA) = viewModelScope.launch {
        gameAGQBARepository.insertGameAGQBA(game)
    }

    fun newGameAGQBA() = viewModelScope.launch {
        // TODO: have real team input
        val team1 = gameAGQBARepository.insertTeam(Team(null, "team 1!"))
        val team2 = gameAGQBARepository.insertTeam(Team(null, "team 2!"))
        val gameId = gameAGQBARepository.insertGame(Game(null, team1, team2))
        val round1Tossups = List(20) { questionNumber ->
            Tossup(
                null,
                gameId,
                questionNumber,
                1,
                TeamAnswered.NONE,
                null
            )
        }
        gameAGQBARepository.insertTossupList(round1Tossups)
    }

    class TossupViewModelFactory(private val repository: GameAGQBARepository) : ViewModelProvider.Factory{
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

}