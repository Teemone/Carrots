package com.example.carrots.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carrots.R
import com.example.carrots.databinding.FragmentContactInformationBinding
import com.example.carrots.model.VegViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ContactInformationFragment : Fragment() {
    private lateinit var binding: FragmentContactInformationBinding
    private val sharedViewModel: VegViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactInformationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = sharedViewModel
        binding.contactInfoFragment = this@ContactInformationFragment


    }

    /**
     * Checks if the editTexts are empty, returns an error if so
     * Clears the error when criteria is met i.e length of text is >= 2
     */
    private fun handleEditText(): Boolean {

        binding.etName.let {
            if (!isValid(it)) {
                setError(binding.tilName)
            } else
                clearError(binding.tilName)

            it.setOnKeyListener { _, _, _ ->
                if (isValid(it)) {
                    clearError(binding.tilName)
                }
                false
            }
        }


        binding.etAddress.let {
            if (!isValid(it)) {
                setError(binding.tilAddress)
            } else
                clearError(binding.tilAddress)

            it.setOnKeyListener { _, _, _ ->
                if (isValid(it)) {
                    clearError(binding.tilAddress)
                }
                false
            }
        }

        binding.etPhoneNumber.let {
            if (!isValid(it)) {
                setError(binding.tilPhoneNumber)
            } else
                clearError(binding.tilPhoneNumber)

            it.setOnKeyListener { _, _, _ ->
                if (isValid(it)) {
                    clearError(binding.tilPhoneNumber)
                }
                false
            }
        }
        return isValid(binding.etName) && isValid(binding.etAddress) && isValid(binding.etPhoneNumber)
    }

    /**
     * Navigate to the next screen (if editTexts are populated as expected)
     */
    fun next() {
        if (handleEditText()) {
            sharedViewModel.setName(binding.etName.text.toString())
            sharedViewModel.setPhoneNumber(binding.etPhoneNumber.text.toString())
            sharedViewModel.setAddress(binding.etAddress.text.toString())

            val action = ContactInformationFragmentDirections.actionContactInformationFragmentToOrderSummaryFragment()
            findNavController().navigate(action)
        }
    }

    /**
     * Cancel the order and return to the home fragment
     */
    fun cancel() {
        sharedViewModel.reset()
        findNavController()
            .navigate(R.id.action_contactInformationFragment_to_homeFragment)
    }

    /**
     * Returns if the editText matches criteria
     */
    private fun isValid(view: View): Boolean {
        val editText = view as TextInputEditText
        return editText.text != null && editText.length() >= 2
    }

    /**
     * Sets the error attribute of the textInputLayout
     */
    private fun setError(v: View) {
        val til = v as TextInputLayout
        til.error = getString(R.string.provide_details)
    }

    /**
     * Clears textInputLayout error
     */
    private fun clearError(v: View) {
        val til = v as TextInputLayout
        til.error = null
    }

}