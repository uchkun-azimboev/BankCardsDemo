package com.example.bankcardsdemo.repository

import com.example.bankcardsdemo.model.Card
import com.example.bankcardsdemo.utils.UiState

interface CardRepository {
    fun addCard(card: Card, result: (UiState<Pair<Card, String>>) -> Unit)
    fun getCards(result: (UiState<List<Card>>) -> Unit)
}