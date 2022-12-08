package com.example.quizbowlscoresheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.quizbowlscoresheet.database.repositories.agqbagame.GameAGQBARepository

class GameOverviewViewModel(private val gameAGQBARepository: GameAGQBARepository, private val gameId: Long): ViewModel() {

    val currentGame = gameAGQBARepository.getGameAGQBAById(gameId).asLiveData()

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
