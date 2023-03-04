package com.example.carrots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.carrots.databinding.FragmentOrderSpecificationsBinding
import com.example.carrots.model.VegViewModel
import com.google.android.material.chip.Chip

class OrderSpecificationsFragment : Fragment() {

    private lateinit var binding: FragmentOrderSpecificationsBinding
    private val sharedViewModel: VegViewModel by activityViewModels()
    private var args: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        val chipList = binding.cgPickupDate
        repeat(4){
            index -> (chipList[index] as Chip).text = sharedViewModel.pickupDates[index]
        }
    }
}