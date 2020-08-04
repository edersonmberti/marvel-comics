package com.example.marvel.characters

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.marvel.R
import com.example.marvel.characters.adapter.CharactersAdapter
import com.example.marvel.extensions.showToast
import com.example.marvel.services.RetrofitClient
import com.example.marvel.util.EndlessScrollView
import com.example.marvel.util.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_characters.*

class CharactersFragment : Fragment() {

    private lateinit var charactersViewModel: CharactersViewModel
    private lateinit var charactersAdapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_characters, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        charactersViewModel = ViewModelProvider(
            this,
            CharacterViewModelFactory(RetrofitClient.getCharacterService())
        ).get(CharactersViewModel::class.java)

        setUI()
        initiateObservers()

        if (savedInstanceState == null) {
            charactersViewModel.loadCharacters()
        }
    }

    private fun initiateObservers() {
        charactersViewModel.run {
            observeToShowToast(showMessageHasReachedEnd, R.string.no_more_characters_available)
            observeToShowToast(showMessageLoading, R.string.loading)
            observeToShowToast(showMessageError, R.string.something_went_wrong)

            characters.observe(viewLifecycleOwner, Observer { listCharacters ->
                charactersAdapter.addItems(listCharacters.toMutableList())
            })
        }
    }

    private fun observeToShowToast(liveData: LiveData<Boolean>, messageId: Int) {
        liveData.observe(viewLifecycleOwner, Observer { shouldShow ->
            if (shouldShow) activity?.showToast(messageId)
        })
    }

    private fun setUI() {
        with(rvCharacter) {
            charactersAdapter = CharactersAdapter(
                mutableListOf(),
                this@CharactersFragment::onClick
            )

            adapter = charactersAdapter

            addItemDecoration(
                GridSpacingItemDecoration(
                    resources.getDimensionPixelSize(
                        R.dimen.margin_inside
                    )
                )
            )
        }

        nsvCharacters.setOnScrollChangeListener(object : EndlessScrollView() {
            override fun onLoadMore(page: Int) {
                charactersViewModel.loadCharacters()
            }
        })
    }

    private fun onClick(id: Int) {
        startActivity(Intent(requireContext(), CharacterActivity::class.java).apply {
            putExtra(CHARACTER_ID, id)
        })
    }

}