package com.example.carrots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.carrots.databinding.FragmentOrderSpecificationsBinding
import com.example.carrots.model.VegViewModel

class OrderSpecificationsFragment : Fragment() {

    private lateinit var binding: FragmentOrderSpecificationsBinding
    private val sharedViewModel: VegViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderSpecificationsBinding.inflate(layoutInflater)
        return binding.root
    }
}