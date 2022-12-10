package com.example.quizbowlscoresheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.quizbowlscoresheet.database.repositories.agqbagame.GameAGQBARepository

class LoadGameViewModel(private val gameAGQBARepository: GameAGQBARepository): ViewModel() {

    val gameList = gameAGQBARepository.getAllGamesFlow().asLiveData()

    class LoadGameViewModelFactory(private val repository: GameAGQBARepository) : ViewModelProvider.Factory{
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(LoadGameViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return LoadGameViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

}
