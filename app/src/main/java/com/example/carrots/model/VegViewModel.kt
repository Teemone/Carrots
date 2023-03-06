package com.example.carrots.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carrots.ui.vegArrayList
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

const val VEGETABLE_PRICE = 3.0
const val SAME_DAY_PICKUP_PRICE = 1.25

class VegViewModel: ViewModel() {
    private var _veggies = vegArrayList.apply { shuffle() }
    val veggies = _veggies

    private var _total = MutableLiveData<String>()
    val total: LiveData<String> = _total

    val pickupDates = returnPickupDates()

    private var _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private var _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    init {
        reset()
        calculateTotal()
    }

    /**
     * Initializes all uninitialized properties,
     * it also resets all the shared properties in the viewModel
     */
    fun reset(){
        _date.value = pickupDates[0]
        _quantity.value = 1
        _total.value = ""
    }

    fun setQuantity(value: Int){
        _quantity.value=value
        calculateTotal()
    }
    fun setDate(value: String){
        _date.value=value
        calculateTotal()
    }

    fun isSameDayPickup(): Boolean{
        return _date.value == pickupDates[0]
    }

    /**
     * Calculates the total price and formats it to the appropriate currency
     */
    fun calculateTotal(){
        var price = _quantity.value?.times(VEGETABLE_PRICE)
        if (isSameDayPickup()){
            price?.let { price += SAME_DAY_PICKUP_PRICE }
        }
        _total.value = formatCurrency(price!!)
    }

    /**
     * Returns a currency formatted version of the value provided
     */
    fun formatCurrency(value: Double): String{
        return NumberFormat.getCurrencyInstance().format(value)
    }

    /**
     * Returns a list of dates, starting from today
     */
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