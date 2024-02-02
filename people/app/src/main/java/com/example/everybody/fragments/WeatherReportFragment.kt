package com.example.everybody.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load

import com.example.everybody.R
import com.example.everybody.databinding.FragmentWeatherReportBinding


class WeatherReportFragment : Fragment() {
    private lateinit var binding:FragmentWeatherReportBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_weather_report,container,false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.apply {
            airQualityDetails.text = arguments?.getString("airQuality")
            humidity.text = "Humidity  ${arguments?.getString("humidity")}"
            tempC.text=arguments?.getString("temp_c")
            weatherIcon.load(arguments?.getString("image"))
            windSpeed.text= "Wind speed ${arguments?.getString("windSpeed")} mph"
            climate.text = arguments?.getString("climate")

        }

        binding.backBtnWeather.setOnClickListener {
            findNavController().navigate(R.id.from_weatherReportFragment_to_personDetailsFragment)
        }
    }

}