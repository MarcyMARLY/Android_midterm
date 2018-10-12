package com.example.msise.androidmid

import android.view.View

interface TodoClickListener {
    fun onClick(view: View, position: Int)
}