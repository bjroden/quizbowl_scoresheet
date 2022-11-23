package com.example.quizbowlscoresheet

import androidx.lifecycle.ViewModel
import com.example.quizbowlscoresheet.database.databases.tossups.TossupRepository
import androidx.lifecycle.*
import com.example.quizbowlscoresheet.database.models.Tossup
import kotlinx.coroutines.launch

class TossupViewModel(private val tossupRepository: TossupRepository): ViewModel() {
    fun insert(tossup: Tossup) = viewModelScope.launch {
        tossupRepository.insert(tossup)
    }

    class TossupViewModelFactory(private val repository: TossupRepository) : ViewModelProvider.Factory{
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(TossupViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return TossupViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

}