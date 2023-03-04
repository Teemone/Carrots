package com.example.carrots.model

import androidx.lifecycle.ViewModel
import com.example.carrots.ui.vegArrayList
import java.text.SimpleDateFormat
import java.util.*

const val VEGETABLE_PRICE = 3.0

class VegViewModel: ViewModel() {
    private var _veggies = vegArrayList.apply { shuffle() }
    val veggies = _veggies

    val pickupDates = returnPickupDates()



    private fun returnPickupDates(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E, MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()

        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }
}