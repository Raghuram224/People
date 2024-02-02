package com.example.everybody.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import com.example.everybody.R
import com.example.everybody.databinding.FragmentActionBarBinding


class ActionBarFragment : Fragment() {

    private lateinit var binding: FragmentActionBarBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_action_bar,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }


}