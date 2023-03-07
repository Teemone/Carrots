package com.example.carrots.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carrots.R
import com.example.carrots.databinding.FragmentOrderSummaryBinding
import com.example.carrots.model.VegViewModel

class OrderSummaryFragment : Fragment() {

    private lateinit var binding: FragmentOrderSummaryBinding
    private val sharedViewModel: VegViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOrderSummaryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = sharedViewModel
        binding.orderSummary = this@OrderSummaryFragment
        binding.lifecycleOwner = viewLifecycleOwner

        Toast.makeText(requireContext(),
            "Name: ${sharedViewModel.name.value}\nPhone: ${sharedViewModel.phoneNumber.value}\nAddress: ${sharedViewModel.address.value}"
            ,Toast.LENGTH_SHORT).show()

    }


    /**
     * Cancel the order and return to the home fragment
     */
    fun cancel(){
        sharedViewModel.reset()
        findNavController()
            .navigate(R.id.action_orderSummaryFragment_to_homeFragment)
    }

    fun sendOrder(){

    }

}