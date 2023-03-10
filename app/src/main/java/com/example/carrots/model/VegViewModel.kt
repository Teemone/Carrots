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

class VegViewModel : ViewModel() {
    private var _veggies = vegArrayList.apply { shuffle() }
    val veggies = _veggies

    private var _total = MutableLiveData<String>()
    val total: LiveData<String> = _total

    private var _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private var _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private var _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private var _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private var _address = MutableLiveData<String>()
    val address: LiveData<String> = _address

    private var _vegetableName = MutableLiveData<String>()
    val vegetableName: LiveData<String> = _vegetableName

    val pickupDates = returnPickupDates()


    init {
        reset()
        calculateTotal()
    }

    /**
     * Initializes all uninitialized properties,
     * it also resets all the shared properties in the viewModel
     */
    fun reset() {
        _date.value = pickupDates[0]
        _quantity.value = 1
        _total.value = ""
        _name.value = ""
        _phoneNumber.value = ""
        _address.value = ""
        _vegetableName.value = ""
    }

    fun setVegetableName(name: String) {
        _vegetableName.value = name
    }

    fun setName(name: String) {
        _name.value = name
            .replaceFirstChar { char -> char.uppercase() }
    }

    fun setPhoneNumber(phone: String) {
        _phoneNumber.value = phone
    }

    fun setAddress(address: String) {
        _address.value = address
    }

    fun setQuantity(value: Int) {
        _quantity.value = value
        calculateTotal()
    }

    fun setDate(value: String) {
        _date.value = value
        calculateTotal()
    }

    fun isSameDayPickup(): Boolean {
        return _date.value == pickupDates[0]
    }

    /**
     * Calculates the total price and formats it to the appropriate currency
     */
    fun calculateTotal() {
        var price = _quantity.value?.times(VEGETABLE_PRICE)
        if (isSameDayPickup()) {
            price?.let { price += SAME_DAY_PICKUP_PRICE }
        }
        _total.value = formatCurrency(price!!)
    }

    /**
     * Returns a currency formatted version of the value provided
     */
    fun formatCurrency(value: Double): String {
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