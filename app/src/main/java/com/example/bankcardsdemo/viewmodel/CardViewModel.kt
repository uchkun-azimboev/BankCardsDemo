package com.example.bankcardsdemo.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bankcardsdemo.model.Card
import com.example.bankcardsdemo.repository.CardRepository
import com.example.bankcardsdemo.utils.ConnectionLiveData
import com.example.bankcardsdemo.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(private val repository: CardRepository, private val application: Application) : ViewModel() {

    private val _addCard = MutableLiveData<UiState<Pair<Card, String>>>()
    val addCard: LiveData<UiState<Pair<Card, String>>>
        get() = _addCard

    private val _cards = MutableLiveData<UiState<List<Card>>>()
    val cards: LiveData<UiState<List<Card>>>
        get() = _cards



    fun addCard(card: Card) {
        _addCard.value = UiState.Loading
        repository.addCard(card) {
            _addCard.value = it
        }
    }

    fun getCards() {
        _cards.value = UiState.Loading
        repository.getCards {
            _cards.value = it
        }
    }
}