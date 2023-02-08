package com.example.bankcardsdemo.ui.addcard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankcardsdemo.R
import com.example.bankcardsdemo.databinding.FragmentAddCardBinding
import com.example.bankcardsdemo.model.Card
import com.example.bankcardsdemo.ui.addcard.CardTextWatcher.Companion.CARD_NUMBER
import com.example.bankcardsdemo.ui.addcard.CardTextWatcher.Companion.EXPIRATION_DATE
import com.example.bankcardsdemo.utils.CardTypes
import com.example.bankcardsdemo.utils.Extensions.click
import com.example.bankcardsdemo.utils.Extensions.hide
import com.example.bankcardsdemo.utils.Extensions.removeSpaces
import com.example.bankcardsdemo.utils.Extensions.show
import com.example.bankcardsdemo.utils.Extensions.toast
import com.example.bankcardsdemo.utils.Fields
import com.example.bankcardsdemo.utils.UiState
import com.example.bankcardsdemo.viewmodel.CardViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class AddCardFragment : Fragment() {

    private var _bn: FragmentAddCardBinding? = null
    private val bn get() = _bn!!

    private val viewModel: CardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn = FragmentAddCardBinding.inflate(inflater, container, false)
        return bn.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observer()
    }

    private fun initViews() = bn.apply {
        ivBack click { requireActivity().onBackPressed() }
        cardNumber.addTextChangedListener(CardTextWatcher(cardNumber, CARD_NUMBER))
        expirationDate.addTextChangedListener(CardTextWatcher(expirationDate, EXPIRATION_DATE))

        btnAddCard click {
            if (isValidCard()) {
                val card = Card(
                    cardNumber.text.toString().removeSpaces(),
                    expirationDate.text.toString(),
                    getCardType(cardNumber.text.toString()),
                    100000.0
                )

                viewModel.addCard(card)
            } else {
                toast(getString(R.string.str_info_invalid_card))
            }
        }
    }

    private fun isValidCard(): Boolean {
        if (bn.cardNumber.text.toString().removeSpaces().length != 16) return false

        val date = bn.expirationDate.text.toString()
        if (date.length != 5) return false

        val month = date.substring(0, 2).toInt()
        val year = date.substring(3, 5).toInt()

        if (month > 12 || month == 0) return false

        val cYear = Calendar.getInstance().get(Calendar.YEAR) % 100
        if (year < cYear) return false

        val cMonth = Calendar.getInstance().get(Calendar.MONTH)
        if (month <= cMonth) return false

        return true
    }

    private fun getCardType(toString: String): CardTypes {
        return when (toString.substring(0, 4)) {
            Fields.UZCARD -> CardTypes.UZCARD
            Fields.HUMO -> CardTypes.HUMO
            else -> CardTypes.VISA
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }

    private fun observer() {
        viewModel.addCard.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    bn.progressbar.hide()
                    bn.btnAddCard.show()
                    toast(state.data.second)
                    findNavController().navigate(R.id.action_addCardFragment_to_cardListFragment)
                }
                is UiState.Failure -> {
                    bn.progressbar.hide()
                    bn.btnAddCard.show()
                    toast(state.error)
                }
                is UiState.Loading -> {
                    bn.btnAddCard.hide()
                    bn.progressbar.show()
                }
            }
        }
    }

}