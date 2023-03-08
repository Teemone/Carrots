package com.example.carrots.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carrots.R
import com.example.carrots.databinding.FragmentOrderSummaryBinding
import com.example.carrots.model.VegViewModel

class OrderSummaryFragment : Fragment() {

    private lateinit var binding: FragmentOrderSummaryBinding
    private val sharedViewModel: VegViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentOrderSummaryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = sharedViewModel
        binding.orderSummary = this@OrderSummaryFragment
        binding.lifecycleOwner = viewLifecycleOwner

    }


    /**
     * Cancel the order and return to the home fragment
     */
    fun cancel() {
        sharedViewModel.reset()
        findNavController()
            .navigate(R.id.action_orderSummaryFragment_to_homeFragment)
    }

    /**
     * Send the order to the appropriate app to process the order
     * i.e. Gmail or any other emailing app
     */

    fun sendOrder() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(
                Intent.EXTRA_SUBJECT,
                getString(R.string.email_subject, sharedViewModel.name.value)
            )
            putExtra(
                Intent.EXTRA_TEXT, getString(
                    R.string.email_body,
                    sharedViewModel.vegetableName.value,
                    sharedViewModel.quantity.value.toString(),
                    sharedViewModel.date.value,
                    sharedViewModel.phoneNumber.value,
                    sharedViewModel.address.value
                )
            )
        }

        // Checks if the device has an appropriate app to process the intent request
        if (activity?.packageManager?.resolveActivity(intent, 0) != null)
            startActivity(intent)


    }

}