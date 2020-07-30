package com.example.marvel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.marvel.characters.CharactersFragment
import com.example.marvel.extensions.resetIcons
import com.example.marvel.favorites.FavoritesFragment
import com.example.marvel.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val charactersFragment by lazy { CharactersFragment() }
    private val favoritesFragment by lazy { FavoritesFragment() }
    private val searchFragment by lazy { SearchFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bnvCharacters.setOnNavigationItemSelectedListener { menuItem ->

            bnvCharacters.resetIcons()

            val fragment: Fragment = when (menuItem.itemId) {
                R.id.page_characters -> {
                    menuItem.setIcon(R.drawable.ic_person_filled)
                    charactersFragment
                }
                R.id.page_favorites -> {
                    menuItem.setIcon(R.drawable.ic_favorite_filled)
                    favoritesFragment
                }
                else -> {
                    menuItem.setIcon(R.drawable.ic_search_filled)
                    searchFragment
                }
            }

            goTo(fragment)

            true
        }

        bnvCharacters.selectedItemId = R.id.page_characters
    }

    private fun goTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().run {
            replace(R.id.flFragmentContainer, fragment)
            commit()
        }
    }
}