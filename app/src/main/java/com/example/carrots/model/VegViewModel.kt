package com.example.carrots.model

import androidx.lifecycle.ViewModel
import com.example.carrots.ui.vegArrayList

class VegViewModel: ViewModel() {
    private var _veggies = vegArrayList.apply { shuffle() }
    val veggies = _veggies


}