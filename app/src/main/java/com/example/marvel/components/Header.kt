package com.example.marvel.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.marvel.R
import kotlinx.android.synthetic.main.header.view.*

class Header @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        val view = LayoutInflater.from(context).inflate(R.layout.header, this, true)

        attrs?.let { attributeSet ->
            context.obtainStyledAttributes(attributeSet, R.styleable.Header).run {
                view.tvTitle.text = getString(R.styleable.Header_text)
                recycle()
            }
        }
    }
}