package com.example.bankcardsdemo.ui.cardlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bankcardsdemo.R
import com.example.bankcardsdemo.databinding.ItemCardViewBinding
import com.example.bankcardsdemo.model.Card
import com.example.bankcardsdemo.utils.CardTypes
import com.example.bankcardsdemo.utils.Extensions.formatCreditCardNumber

class CardAdapter : ListAdapter<Card, CardAdapter.CardViewHolder>(CardDiffCallBack()) {


    inner class CardViewHolder(private val bn: ItemCardViewBinding) :
        RecyclerView.ViewHolder(bn.root) {

        fun bind(card: Card) = bn.apply {

            tvCardNumber.text = card.cardNumber.formatCreditCardNumber()
            tvCardDate.text = card.cardDate
            tvBalance.text = card.balance.toString()

            ivCardType.setImageResource(
                when (card.cardType) {
                    CardTypes.UZCARD -> R.drawable.img_uzcard
                    CardTypes.HUMO -> R.drawable.img_humo
                    else -> R.drawable.img_visa
                }
            )

            ivCardFon.setImageResource(
                when (card.cardType) {
                    CardTypes.UZCARD -> R.color.purple_500
                    CardTypes.HUMO -> R.color.teal_700
                    else -> R.color.gold
                }
            )
        }
    }

    private class CardDiffCallBack : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            ItemCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}