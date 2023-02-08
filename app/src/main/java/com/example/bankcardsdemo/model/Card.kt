package com.example.bankcardsdemo.model

import com.example.bankcardsdemo.utils.CardTypes


data class Card(
    var cardNumber: String,
    var cardDate: String,
    var cardType: CardTypes,
    var balance: Double
) {
    constructor() : this("", "", CardTypes.VISA, 0.0)
}
