package com.example.bankcardsdemo.repository

import android.app.Application
import com.example.bankcardsdemo.R
import com.example.bankcardsdemo.model.Card
import com.example.bankcardsdemo.utils.ConnectionLiveData
import com.example.bankcardsdemo.utils.Extensions.toast
import com.example.bankcardsdemo.utils.Fields
import com.example.bankcardsdemo.utils.UiState
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class CardRepositoryImp(
    private val database: FirebaseDatabase,
    private val application: Application
) : CardRepository {

    override fun addCard(card: Card, result: (UiState<Pair<Card, String>>) -> Unit) {
        database.getReference(Fields.CARDS).child(card.cardNumber)
            .setValue(card).addOnSuccessListener {
                result.invoke(
                    UiState.Success(
                        Pair(card, application.getString(R.string.str_succes_card_add))
                    )
                )
            }.addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun getCards(result: (UiState<List<Card>>) -> Unit) {
        database.getReference(Fields.CARDS)
            .get().addOnSuccessListener {
                val cards = arrayListOf<Card?>()
                for (item in it.children) {
                    val card = item.getValue(Card::class.java)
                    cards.add(card)
                }
                result.invoke(UiState.Success(cards.filterNotNull()))

            }.addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }
}