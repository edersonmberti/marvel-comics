package com.example.marvel.extensions

import com.example.marvel.R
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.resetIcons() {
    menu.findItem(R.id.page_characters).setIcon(R.drawable.ic_person);
    menu.findItem(R.id.page_favorites).setIcon(R.drawable.ic_favorite);
    menu.findItem(R.id.page_search).setIcon(R.drawable.ic_search);
}
