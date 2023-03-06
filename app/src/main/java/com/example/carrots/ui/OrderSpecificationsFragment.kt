package com.example.carrots.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carrots.R
import com.example.carrots.databinding.FragmentOrderSpecificationsBinding
import com.example.carrots.model.SAME_DAY_PICKUP_PRICE
import com.example.carrots.model.VegViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class OrderSpecificationsFragment : Fragment() {

    private lateinit var binding: FragmentOrderSpecificationsBinding
    private val sharedViewModel: VegViewModel by activityViewModels()
    private var args: Int? = null
    private lateinit var cgPickupDate: ChipGroup
    private lateinit var cgQuantity: ChipGroup
    private lateinit var tvSameDay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get any arguments that may have been passed
        arguments?.apply {
            args = getInt("id", 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderSpecificationsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = sharedViewModel.veggies[args!!]
        binding.ivVegetable.setImageResource(item.image)
        binding.viewModel = sharedViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.orderSpecsFragment = this@OrderSpecificationsFragment
        tvSameDay = binding.tvSameDayPickup
        cgPickupDate = binding.cgPickupDate
        cgQuantity = binding.cgQuantity


        tvSameDay.text = getString(
            R.string.same_day_pickup,
            sharedViewModel.formatCurrency(SAME_DAY_PICKUP_PRICE))

        /*
        Populate text attribute of each chip with the pickup date
         */
        repeat(4){
            index -> (cgPickupDate[index] as Chip).text = sharedViewModel.pickupDates[index]
        }

        handleChipGroups()
    }


    /**
     * Adds an onCheckedChangedListener to the ChipGroups and handles any changes
     */
    private fun handleChipGroups(){
        cgPickupDate.setOnCheckedStateChangeListener {
                group, checkedIds ->
            /*
            Check if they're any checked ids before initialization,
            prevents indexOutOfBoundsException
             */
            if (checkedIds.isNotEmpty()){
                val chip = group.findViewById<Chip>(checkedIds[0])
                sharedViewModel.setDate(chip.text.toString())

                if (sharedViewModel.isSameDayPickup()) {
                    tvSameDay.visibility = View.VISIBLE
                } else {
                    tvSameDay.visibility = View.INVISIBLE
                }
            }
                sharedViewModel.calculateTotal()

        }

        cgQuantity.setOnCheckedStateChangeListener {
                group, checkedIds ->
            /*
            Check if they're any checked ids before initialization,
            prevents indexOutOfBoundsException
             */
            if (checkedIds.isNotEmpty()){
                val chip = group.findViewById<Chip>(checkedIds[0])
                sharedViewModel.setQuantity(chip.text.toString().toInt())
                sharedViewModel.calculateTotal()
            }


        }
    }

    /**
     * Navigate to the next screen
     */
    fun next(){
        findNavController()
            .navigate(R.id.action_orderSpecificationsFragment_to_contactInformationFragment)
    }

    /**
     * Cancel the order and return to the home fragment
     */
    fun cancel(){
        sharedViewModel.reset()
        findNavController().navigate(R.id.action_orderSpecificationsFragment_to_homeFragment)
    }
}