package com.example.bankcardsdemo.ui.cardlist

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.bankcardsdemo.R
import com.example.bankcardsdemo.databinding.FragmentCardListBinding
import com.example.bankcardsdemo.model.Card
import com.example.bankcardsdemo.utils.Extensions.click
import com.example.bankcardsdemo.utils.Extensions.hide
import com.example.bankcardsdemo.utils.Extensions.show
import com.example.bankcardsdemo.utils.Extensions.toast
import com.example.bankcardsdemo.utils.UiState
import com.example.bankcardsdemo.viewmodel.CardViewModel
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardListFragment : Fragment() {

    private var _bn: FragmentCardListBinding? = null
    private val bn get() = _bn!!

    private val viewModel: CardViewModel by viewModels()
    private val rvAdapter by lazy { CardAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn = FragmentCardListBinding.inflate(inflater, container, false)
        return bn.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getCards()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observer()
    }

    private fun initViews() = bn.apply {

        ivAddCard click {
            findNavController().navigate(R.id.action_cardListFragment_to_addCardFragment)
        }

        ivRefresh click {
            viewModel.getCards()
        }

        rvCard.adapter = rvAdapter
    }

    private fun observer() {
        viewModel.cards.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    bn.progressbar.show()
                }
                is UiState.Success -> {
                    bn.progressbar.hide()
                    rvAdapter.submitList(state.data)
                }
                is UiState.Failure -> {
                    bn.progressbar.hide()
                    toast(state.error)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }
}