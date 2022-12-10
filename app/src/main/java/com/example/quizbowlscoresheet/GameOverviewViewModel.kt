package com.example.quizbowlscoresheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.quizbowlscoresheet.database.repositories.agqbagame.GameAGQBARepository
import kotlinx.coroutines.launch

class GameOverviewViewModel(private val gameAGQBARepository: GameAGQBARepository, private val gameId: Long): ViewModel() {

    val currentGame = gameAGQBARepository.getGameAGQBAById(gameId).asLiveData()

    fun changeTeam1Name(newName: String) = viewModelScope.launch {
        currentGame.value?.team1?.let {
            gameAGQBARepository.updateTeam(it.copy(name = newName))
        }
    }

    fun changeTeam2Name(newName: String) = viewModelScope.launch {
        currentGame.value?.team2?.let {
            gameAGQBARepository.updateTeam(it.copy(name = newName))
        }
    }

    class GameOverviewViewModelFactory(private val repository: GameAGQBARepository, private val gameId: Long) : ViewModelProvider.Factory{
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(GameOverviewViewModelFactory::class.java)){
                @Suppress("UNCHECKED_CAST")
                return GameOverviewViewModel(repository, gameId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

}
