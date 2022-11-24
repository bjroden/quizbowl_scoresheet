package com.example.quizbowlscoresheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import com.example.quizbowlscoresheet.database.agqbagame.GameAGQBARepository
import com.example.quizbowlscoresheet.database.models.Game
import kotlinx.coroutines.launch

class MainActivityViewModel(private val tossupRepository: GameAGQBARepository): ViewModel() {
    fun insert(game: Game) = viewModelScope.launch {
        tossupRepository.insert(game)
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