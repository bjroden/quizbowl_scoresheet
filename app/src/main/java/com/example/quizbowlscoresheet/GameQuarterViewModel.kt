package com.example.quizbowlscoresheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.quizbowlscoresheet.database.models.LightningQuestion
import com.example.quizbowlscoresheet.database.models.Tossup
import com.example.quizbowlscoresheet.database.repositories.agqbagame.GameAGQBARepository
import kotlinx.coroutines.launch

class GameQuarterViewModel(private val repository: GameAGQBARepository, private val gameId: Long) : ViewModel() {

    val currentGame = repository.getGameAGQBAById(gameId).asLiveData()

    fun updateTossup(tossup: Tossup) = viewModelScope.launch {
        repository.updateTossup(tossup)
    }

    fun updateLightningQuestion(lightningQuestion: LightningQuestion) = viewModelScope.launch {
        repository.updateLightningQuestion(lightningQuestion)
    }

    class GameQuarterViewModelFactory(private val repository: GameAGQBARepository, private val gameId: Long) : ViewModelProvider.Factory{
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(GameOverviewViewModel.GameOverviewViewModelFactory::class.java)){
                @Suppress("UNCHECKED_CAST")
                return GameQuarterViewModel(repository, gameId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}