package com.example.marvel.characters

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marvel.R
import com.example.marvel.characters.adpter.CharactersAdapter
import com.example.marvel.extensions.showToast
import com.example.marvel.model.CharactersDataContainer
import com.example.marvel.model.CharactersResponse
import com.example.marvel.services.RetrofitClient
import com.example.marvel.util.EndlessScrollView
import com.example.marvel.util.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_characters.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TS = "thesoer"
private const val API_KEY = "013f3cbc470985603d11f1b0c58a816b"
private const val HASH = "8ecb9d1ae1393b6fd4178945a3fcc1ee"
private const val INITIAL_OFFSET = 0

class CharactersFragment : Fragment() {

    private lateinit var charactersAdapter: CharactersAdapter
    private var hasNextPage: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_characters, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
        getCharacters()
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
                getCharacters(page)
            }
        })
    }

    private fun getCharacters(page: Int = INITIAL_OFFSET) {
        if (hasNextPage.not()) {
            activity?.showToast(R.string.no_more_characters_available)
            return
        }

        activity?.showToast(messageId = R.string.loading)
        RetrofitClient
            .getCharacterService()
            .getAll(
                TS, API_KEY, HASH, page
            )
            .enqueue(object : Callback<CharactersResponse> {
                override fun onResponse(
                    call: Call<CharactersResponse>?,
                    response: Response<CharactersResponse>?
                ) {
                    response?.takeIf { it.isSuccessful }?.run {
                        body()?.run {
                            checkNextPage(this.data)
                            charactersAdapter.addItems(this.data.characters)
                        }
                    } ?: activity?.showToast()
                }

                override fun onFailure(call: Call<CharactersResponse>?, t: Throwable?) {
                    activity?.showToast()
                }
            })
    }

    private fun onClick(id: Int) {
        startActivity(Intent(requireContext(), CharacterActivity::class.java).apply {
            putExtra(CHARACTER_ID, id)
        })
    }

    private fun checkNextPage(data: CharactersDataContainer) {
        hasNextPage = data.run {
            val totalPages = total / limit

             totalPages > offset + 1
        }
    }

}